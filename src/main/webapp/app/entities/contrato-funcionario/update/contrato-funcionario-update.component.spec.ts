import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IAgenteIntegracaoEstagio } from 'app/entities/agente-integracao-estagio/agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from 'app/entities/agente-integracao-estagio/service/agente-integracao-estagio.service';
import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/service/instituicao-ensino.service';
import { IContratoFuncionario } from '../contrato-funcionario.model';
import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';
import { ContratoFuncionarioFormService } from './contrato-funcionario-form.service';

import { ContratoFuncionarioUpdateComponent } from './contrato-funcionario-update.component';

describe('ContratoFuncionario Management Update Component', () => {
  let comp: ContratoFuncionarioUpdateComponent;
  let fixture: ComponentFixture<ContratoFuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratoFuncionarioFormService: ContratoFuncionarioFormService;
  let contratoFuncionarioService: ContratoFuncionarioService;
  let funcionarioService: FuncionarioService;
  let agenteIntegracaoEstagioService: AgenteIntegracaoEstagioService;
  let instituicaoEnsinoService: InstituicaoEnsinoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratoFuncionarioUpdateComponent],
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
      .overrideTemplate(ContratoFuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratoFuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratoFuncionarioFormService = TestBed.inject(ContratoFuncionarioFormService);
    contratoFuncionarioService = TestBed.inject(ContratoFuncionarioService);
    funcionarioService = TestBed.inject(FuncionarioService);
    agenteIntegracaoEstagioService = TestBed.inject(AgenteIntegracaoEstagioService);
    instituicaoEnsinoService = TestBed.inject(InstituicaoEnsinoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const contratoFuncionario: IContratoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 13570 };
      contratoFuncionario.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 26058 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AgenteIntegracaoEstagio query and add missing value', () => {
      const contratoFuncionario: IContratoFuncionario = { id: 456 };
      const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = { id: 11962 };
      contratoFuncionario.agenteIntegracaoEstagio = agenteIntegracaoEstagio;

      const agenteIntegracaoEstagioCollection: IAgenteIntegracaoEstagio[] = [{ id: 10807 }];
      jest
        .spyOn(agenteIntegracaoEstagioService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: agenteIntegracaoEstagioCollection })));
      const additionalAgenteIntegracaoEstagios = [agenteIntegracaoEstagio];
      const expectedCollection: IAgenteIntegracaoEstagio[] = [...additionalAgenteIntegracaoEstagios, ...agenteIntegracaoEstagioCollection];
      jest.spyOn(agenteIntegracaoEstagioService, 'addAgenteIntegracaoEstagioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      expect(agenteIntegracaoEstagioService.query).toHaveBeenCalled();
      expect(agenteIntegracaoEstagioService.addAgenteIntegracaoEstagioToCollectionIfMissing).toHaveBeenCalledWith(
        agenteIntegracaoEstagioCollection,
        ...additionalAgenteIntegracaoEstagios.map(expect.objectContaining),
      );
      expect(comp.agenteIntegracaoEstagiosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call InstituicaoEnsino query and add missing value', () => {
      const contratoFuncionario: IContratoFuncionario = { id: 456 };
      const instituicaoEnsino: IInstituicaoEnsino = { id: 8207 };
      contratoFuncionario.instituicaoEnsino = instituicaoEnsino;

      const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [{ id: 14834 }];
      jest.spyOn(instituicaoEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: instituicaoEnsinoCollection })));
      const additionalInstituicaoEnsinos = [instituicaoEnsino];
      const expectedCollection: IInstituicaoEnsino[] = [...additionalInstituicaoEnsinos, ...instituicaoEnsinoCollection];
      jest.spyOn(instituicaoEnsinoService, 'addInstituicaoEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      expect(instituicaoEnsinoService.query).toHaveBeenCalled();
      expect(instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        instituicaoEnsinoCollection,
        ...additionalInstituicaoEnsinos.map(expect.objectContaining),
      );
      expect(comp.instituicaoEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contratoFuncionario: IContratoFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 25586 };
      contratoFuncionario.funcionario = funcionario;
      const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = { id: 11644 };
      contratoFuncionario.agenteIntegracaoEstagio = agenteIntegracaoEstagio;
      const instituicaoEnsino: IInstituicaoEnsino = { id: 14327 };
      contratoFuncionario.instituicaoEnsino = instituicaoEnsino;

      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.agenteIntegracaoEstagiosSharedCollection).toContain(agenteIntegracaoEstagio);
      expect(comp.instituicaoEnsinosSharedCollection).toContain(instituicaoEnsino);
      expect(comp.contratoFuncionario).toEqual(contratoFuncionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratoFuncionario>>();
      const contratoFuncionario = { id: 123 };
      jest.spyOn(contratoFuncionarioFormService, 'getContratoFuncionario').mockReturnValue(contratoFuncionario);
      jest.spyOn(contratoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(contratoFuncionarioFormService.getContratoFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratoFuncionarioService.update).toHaveBeenCalledWith(expect.objectContaining(contratoFuncionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratoFuncionario>>();
      const contratoFuncionario = { id: 123 };
      jest.spyOn(contratoFuncionarioFormService, 'getContratoFuncionario').mockReturnValue({ id: null });
      jest.spyOn(contratoFuncionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratoFuncionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratoFuncionario }));
      saveSubject.complete();

      // THEN
      expect(contratoFuncionarioFormService.getContratoFuncionario).toHaveBeenCalled();
      expect(contratoFuncionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratoFuncionario>>();
      const contratoFuncionario = { id: 123 };
      jest.spyOn(contratoFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratoFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratoFuncionarioService.update).toHaveBeenCalled();
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

    describe('compareAgenteIntegracaoEstagio', () => {
      it('Should forward to agenteIntegracaoEstagioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agenteIntegracaoEstagioService, 'compareAgenteIntegracaoEstagio');
        comp.compareAgenteIntegracaoEstagio(entity, entity2);
        expect(agenteIntegracaoEstagioService.compareAgenteIntegracaoEstagio).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareInstituicaoEnsino', () => {
      it('Should forward to instituicaoEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(instituicaoEnsinoService, 'compareInstituicaoEnsino');
        comp.compareInstituicaoEnsino(entity, entity2);
        expect(instituicaoEnsinoService.compareInstituicaoEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
