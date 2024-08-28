import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';
import { DescontoPeriodoPagamentoService } from '../service/desconto-periodo-pagamento.service';
import { DescontoPeriodoPagamentoFormService } from './desconto-periodo-pagamento-form.service';

import { DescontoPeriodoPagamentoUpdateComponent } from './desconto-periodo-pagamento-update.component';

describe('DescontoPeriodoPagamento Management Update Component', () => {
  let comp: DescontoPeriodoPagamentoUpdateComponent;
  let fixture: ComponentFixture<DescontoPeriodoPagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let descontoPeriodoPagamentoFormService: DescontoPeriodoPagamentoFormService;
  let descontoPeriodoPagamentoService: DescontoPeriodoPagamentoService;
  let periodoPagamentoService: PeriodoPagamentoService;
  let planoAssinaturaContabilService: PlanoAssinaturaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DescontoPeriodoPagamentoUpdateComponent],
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
      .overrideTemplate(DescontoPeriodoPagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DescontoPeriodoPagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    descontoPeriodoPagamentoFormService = TestBed.inject(DescontoPeriodoPagamentoFormService);
    descontoPeriodoPagamentoService = TestBed.inject(DescontoPeriodoPagamentoService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);
    planoAssinaturaContabilService = TestBed.inject(PlanoAssinaturaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PeriodoPagamento query and add missing value', () => {
      const descontoPeriodoPagamento: IDescontoPeriodoPagamento = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 4733 };
      descontoPeriodoPagamento.periodoPagamento = periodoPagamento;

      const periodoPagamentoCollection: IPeriodoPagamento[] = [{ id: 20811 }];
      jest.spyOn(periodoPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoPagamentoCollection })));
      const additionalPeriodoPagamentos = [periodoPagamento];
      const expectedCollection: IPeriodoPagamento[] = [...additionalPeriodoPagamentos, ...periodoPagamentoCollection];
      jest.spyOn(periodoPagamentoService, 'addPeriodoPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPeriodoPagamento });
      comp.ngOnInit();

      expect(periodoPagamentoService.query).toHaveBeenCalled();
      expect(periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoPagamentoCollection,
        ...additionalPeriodoPagamentos.map(expect.objectContaining),
      );
      expect(comp.periodoPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAssinaturaContabil query and add missing value', () => {
      const descontoPeriodoPagamento: IDescontoPeriodoPagamento = { id: 456 };
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 12075 };
      descontoPeriodoPagamento.planoAssinaturaContabil = planoAssinaturaContabil;

      const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [{ id: 21349 }];
      jest
        .spyOn(planoAssinaturaContabilService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: planoAssinaturaContabilCollection })));
      const additionalPlanoAssinaturaContabils = [planoAssinaturaContabil];
      const expectedCollection: IPlanoAssinaturaContabil[] = [...additionalPlanoAssinaturaContabils, ...planoAssinaturaContabilCollection];
      jest.spyOn(planoAssinaturaContabilService, 'addPlanoAssinaturaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPeriodoPagamento });
      comp.ngOnInit();

      expect(planoAssinaturaContabilService.query).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoAssinaturaContabilCollection,
        ...additionalPlanoAssinaturaContabils.map(expect.objectContaining),
      );
      expect(comp.planoAssinaturaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const descontoPeriodoPagamento: IDescontoPeriodoPagamento = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 6096 };
      descontoPeriodoPagamento.periodoPagamento = periodoPagamento;
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 17932 };
      descontoPeriodoPagamento.planoAssinaturaContabil = planoAssinaturaContabil;

      activatedRoute.data = of({ descontoPeriodoPagamento });
      comp.ngOnInit();

      expect(comp.periodoPagamentosSharedCollection).toContain(periodoPagamento);
      expect(comp.planoAssinaturaContabilsSharedCollection).toContain(planoAssinaturaContabil);
      expect(comp.descontoPeriodoPagamento).toEqual(descontoPeriodoPagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPeriodoPagamento>>();
      const descontoPeriodoPagamento = { id: 123 };
      jest.spyOn(descontoPeriodoPagamentoFormService, 'getDescontoPeriodoPagamento').mockReturnValue(descontoPeriodoPagamento);
      jest.spyOn(descontoPeriodoPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPeriodoPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPeriodoPagamento }));
      saveSubject.complete();

      // THEN
      expect(descontoPeriodoPagamentoFormService.getDescontoPeriodoPagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(descontoPeriodoPagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(descontoPeriodoPagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPeriodoPagamento>>();
      const descontoPeriodoPagamento = { id: 123 };
      jest.spyOn(descontoPeriodoPagamentoFormService, 'getDescontoPeriodoPagamento').mockReturnValue({ id: null });
      jest.spyOn(descontoPeriodoPagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPeriodoPagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPeriodoPagamento }));
      saveSubject.complete();

      // THEN
      expect(descontoPeriodoPagamentoFormService.getDescontoPeriodoPagamento).toHaveBeenCalled();
      expect(descontoPeriodoPagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPeriodoPagamento>>();
      const descontoPeriodoPagamento = { id: 123 };
      jest.spyOn(descontoPeriodoPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPeriodoPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(descontoPeriodoPagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePeriodoPagamento', () => {
      it('Should forward to periodoPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoPagamentoService, 'comparePeriodoPagamento');
        comp.comparePeriodoPagamento(entity, entity2);
        expect(periodoPagamentoService.comparePeriodoPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoAssinaturaContabil', () => {
      it('Should forward to planoAssinaturaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAssinaturaContabilService, 'comparePlanoAssinaturaContabil');
        comp.comparePlanoAssinaturaContabil(entity, entity2);
        expect(planoAssinaturaContabilService.comparePlanoAssinaturaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
