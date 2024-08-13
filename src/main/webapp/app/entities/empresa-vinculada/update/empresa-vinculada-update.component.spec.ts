import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { EmpresaVinculadaService } from '../service/empresa-vinculada.service';
import { IEmpresaVinculada } from '../empresa-vinculada.model';
import { EmpresaVinculadaFormService } from './empresa-vinculada-form.service';

import { EmpresaVinculadaUpdateComponent } from './empresa-vinculada-update.component';

describe('EmpresaVinculada Management Update Component', () => {
  let comp: EmpresaVinculadaUpdateComponent;
  let fixture: ComponentFixture<EmpresaVinculadaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaVinculadaFormService: EmpresaVinculadaFormService;
  let empresaVinculadaService: EmpresaVinculadaService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpresaVinculadaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmpresaVinculadaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaVinculadaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaVinculadaFormService = TestBed.inject(EmpresaVinculadaFormService);
    empresaVinculadaService = TestBed.inject(EmpresaVinculadaService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const empresaVinculada: IEmpresaVinculada = { id: 456 };
      const funcionario: IFuncionario = { id: 15449 };
      empresaVinculada.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 32183 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaVinculada });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empresaVinculada: IEmpresaVinculada = { id: 456 };
      const funcionario: IFuncionario = { id: 15090 };
      empresaVinculada.funcionario = funcionario;

      activatedRoute.data = of({ empresaVinculada });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.empresaVinculada).toEqual(empresaVinculada);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaVinculada>>();
      const empresaVinculada = { id: 123 };
      jest.spyOn(empresaVinculadaFormService, 'getEmpresaVinculada').mockReturnValue(empresaVinculada);
      jest.spyOn(empresaVinculadaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaVinculada });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresaVinculada }));
      saveSubject.complete();

      // THEN
      expect(empresaVinculadaFormService.getEmpresaVinculada).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaVinculadaService.update).toHaveBeenCalledWith(expect.objectContaining(empresaVinculada));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaVinculada>>();
      const empresaVinculada = { id: 123 };
      jest.spyOn(empresaVinculadaFormService, 'getEmpresaVinculada').mockReturnValue({ id: null });
      jest.spyOn(empresaVinculadaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaVinculada: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresaVinculada }));
      saveSubject.complete();

      // THEN
      expect(empresaVinculadaFormService.getEmpresaVinculada).toHaveBeenCalled();
      expect(empresaVinculadaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaVinculada>>();
      const empresaVinculada = { id: 123 };
      jest.spyOn(empresaVinculadaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaVinculada });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaVinculadaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFuncionario', () => {
      it('Should forward to funcionarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(funcionarioService, 'compareFuncionario');
        comp.compareFuncionario(entity, entity2);
        expect(funcionarioService.compareFuncionario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
