import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IDepartamentoFuncionario } from '../departamento-funcionario.model';
import { DepartamentoFuncionarioService } from '../service/departamento-funcionario.service';
import { DepartamentoFuncionarioFormService } from './departamento-funcionario-form.service';

import { DepartamentoFuncionarioUpdateComponent } from './departamento-funcionario-update.component';

describe('DepartamentoFuncionario Management Update Component', () => {
  let comp: DepartamentoFuncionarioUpdateComponent;
  let fixture: ComponentFixture<DepartamentoFuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let departamentoFuncionarioFormService: DepartamentoFuncionarioFormService;
  let departamentoFuncionarioService: DepartamentoFuncionarioService;
  let funcionarioService: FuncionarioService;
  let departamentoService: DepartamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DepartamentoFuncionarioUpdateComponent],
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
      .overrideTemplate(DepartamentoFuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartamentoFuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    departamentoFuncionarioFormService = TestBed.inject(DepartamentoFuncionarioFormService);
    departamentoFuncionarioService = TestBed.inject(DepartamentoFuncionarioService);
    funcionarioService = TestBed.inject(FuncionarioService);
    departamentoService = TestBed.inject(DepartamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const departamentoFuncionario: IDepartamentoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 28263 };
      departamentoFuncionario.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 18793 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoFuncionario });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Departamento query and add missing value', () => {
      const departamentoFuncionario: IDepartamentoFuncionario = { id: 456 };
      const departamento: IDepartamento = { id: 12576 };
      departamentoFuncionario.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 29710 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoFuncionario });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const departamentoFuncionario: IDepartamentoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 20479 };
      departamentoFuncionario.funcionario = funcionario;
      const departamento: IDepartamento = { id: 18340 };
      departamentoFuncionario.departamento = departamento;

      activatedRoute.data = of({ departamentoFuncionario });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.departamentoFuncionario).toEqual(departamentoFuncionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoFuncionario>>();
      const departamentoFuncionario = { id: 123 };
      jest.spyOn(departamentoFuncionarioFormService, 'getDepartamentoFuncionario').mockReturnValue(departamentoFuncionario);
      jest.spyOn(departamentoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(departamentoFuncionarioFormService.getDepartamentoFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(departamentoFuncionarioService.update).toHaveBeenCalledWith(expect.objectContaining(departamentoFuncionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoFuncionario>>();
      const departamentoFuncionario = { id: 123 };
      jest.spyOn(departamentoFuncionarioFormService, 'getDepartamentoFuncionario').mockReturnValue({ id: null });
      jest.spyOn(departamentoFuncionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoFuncionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(departamentoFuncionarioFormService.getDepartamentoFuncionario).toHaveBeenCalled();
      expect(departamentoFuncionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoFuncionario>>();
      const departamentoFuncionario = { id: 123 };
      jest.spyOn(departamentoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(departamentoFuncionarioService.update).toHaveBeenCalled();
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

    describe('compareDepartamento', () => {
      it('Should forward to departamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(departamentoService, 'compareDepartamento');
        comp.compareDepartamento(entity, entity2);
        expect(departamentoService.compareDepartamento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
