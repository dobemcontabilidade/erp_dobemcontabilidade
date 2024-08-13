import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { DemissaoFuncionarioService } from '../service/demissao-funcionario.service';
import { IDemissaoFuncionario } from '../demissao-funcionario.model';
import { DemissaoFuncionarioFormService } from './demissao-funcionario-form.service';

import { DemissaoFuncionarioUpdateComponent } from './demissao-funcionario-update.component';

describe('DemissaoFuncionario Management Update Component', () => {
  let comp: DemissaoFuncionarioUpdateComponent;
  let fixture: ComponentFixture<DemissaoFuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demissaoFuncionarioFormService: DemissaoFuncionarioFormService;
  let demissaoFuncionarioService: DemissaoFuncionarioService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DemissaoFuncionarioUpdateComponent],
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
      .overrideTemplate(DemissaoFuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemissaoFuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demissaoFuncionarioFormService = TestBed.inject(DemissaoFuncionarioFormService);
    demissaoFuncionarioService = TestBed.inject(DemissaoFuncionarioService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const demissaoFuncionario: IDemissaoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 5843 };
      demissaoFuncionario.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 8052 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demissaoFuncionario });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demissaoFuncionario: IDemissaoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 31653 };
      demissaoFuncionario.funcionario = funcionario;

      activatedRoute.data = of({ demissaoFuncionario });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.demissaoFuncionario).toEqual(demissaoFuncionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemissaoFuncionario>>();
      const demissaoFuncionario = { id: 123 };
      jest.spyOn(demissaoFuncionarioFormService, 'getDemissaoFuncionario').mockReturnValue(demissaoFuncionario);
      jest.spyOn(demissaoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demissaoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demissaoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(demissaoFuncionarioFormService.getDemissaoFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demissaoFuncionarioService.update).toHaveBeenCalledWith(expect.objectContaining(demissaoFuncionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemissaoFuncionario>>();
      const demissaoFuncionario = { id: 123 };
      jest.spyOn(demissaoFuncionarioFormService, 'getDemissaoFuncionario').mockReturnValue({ id: null });
      jest.spyOn(demissaoFuncionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demissaoFuncionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demissaoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(demissaoFuncionarioFormService.getDemissaoFuncionario).toHaveBeenCalled();
      expect(demissaoFuncionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemissaoFuncionario>>();
      const demissaoFuncionario = { id: 123 };
      jest.spyOn(demissaoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demissaoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demissaoFuncionarioService.update).toHaveBeenCalled();
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
