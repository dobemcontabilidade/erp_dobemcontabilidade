import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';
import { DescontoPlanoContabilFormService } from './desconto-plano-contabil-form.service';

import { DescontoPlanoContabilUpdateComponent } from './desconto-plano-contabil-update.component';

describe('DescontoPlanoContabil Management Update Component', () => {
  let comp: DescontoPlanoContabilUpdateComponent;
  let fixture: ComponentFixture<DescontoPlanoContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let descontoPlanoContabilFormService: DescontoPlanoContabilFormService;
  let descontoPlanoContabilService: DescontoPlanoContabilService;
  let periodoPagamentoService: PeriodoPagamentoService;
  let planoContabilService: PlanoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DescontoPlanoContabilUpdateComponent],
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
      .overrideTemplate(DescontoPlanoContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DescontoPlanoContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    descontoPlanoContabilFormService = TestBed.inject(DescontoPlanoContabilFormService);
    descontoPlanoContabilService = TestBed.inject(DescontoPlanoContabilService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);
    planoContabilService = TestBed.inject(PlanoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PeriodoPagamento query and add missing value', () => {
      const descontoPlanoContabil: IDescontoPlanoContabil = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 4859 };
      descontoPlanoContabil.periodoPagamento = periodoPagamento;

      const periodoPagamentoCollection: IPeriodoPagamento[] = [{ id: 15702 }];
      jest.spyOn(periodoPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoPagamentoCollection })));
      const additionalPeriodoPagamentos = [periodoPagamento];
      const expectedCollection: IPeriodoPagamento[] = [...additionalPeriodoPagamentos, ...periodoPagamentoCollection];
      jest.spyOn(periodoPagamentoService, 'addPeriodoPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPlanoContabil });
      comp.ngOnInit();

      expect(periodoPagamentoService.query).toHaveBeenCalled();
      expect(periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoPagamentoCollection,
        ...additionalPeriodoPagamentos.map(expect.objectContaining),
      );
      expect(comp.periodoPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContabil query and add missing value', () => {
      const descontoPlanoContabil: IDescontoPlanoContabil = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 7165 };
      descontoPlanoContabil.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 27302 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPlanoContabil });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const descontoPlanoContabil: IDescontoPlanoContabil = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 1257 };
      descontoPlanoContabil.periodoPagamento = periodoPagamento;
      const planoContabil: IPlanoContabil = { id: 28751 };
      descontoPlanoContabil.planoContabil = planoContabil;

      activatedRoute.data = of({ descontoPlanoContabil });
      comp.ngOnInit();

      expect(comp.periodoPagamentosSharedCollection).toContain(periodoPagamento);
      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
      expect(comp.descontoPlanoContabil).toEqual(descontoPlanoContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContabil>>();
      const descontoPlanoContabil = { id: 123 };
      jest.spyOn(descontoPlanoContabilFormService, 'getDescontoPlanoContabil').mockReturnValue(descontoPlanoContabil);
      jest.spyOn(descontoPlanoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPlanoContabil }));
      saveSubject.complete();

      // THEN
      expect(descontoPlanoContabilFormService.getDescontoPlanoContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(descontoPlanoContabilService.update).toHaveBeenCalledWith(expect.objectContaining(descontoPlanoContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContabil>>();
      const descontoPlanoContabil = { id: 123 };
      jest.spyOn(descontoPlanoContabilFormService, 'getDescontoPlanoContabil').mockReturnValue({ id: null });
      jest.spyOn(descontoPlanoContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPlanoContabil }));
      saveSubject.complete();

      // THEN
      expect(descontoPlanoContabilFormService.getDescontoPlanoContabil).toHaveBeenCalled();
      expect(descontoPlanoContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContabil>>();
      const descontoPlanoContabil = { id: 123 };
      jest.spyOn(descontoPlanoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(descontoPlanoContabilService.update).toHaveBeenCalled();
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

    describe('comparePlanoContabil', () => {
      it('Should forward to planoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContabilService, 'comparePlanoContabil');
        comp.comparePlanoContabil(entity, entity2);
        expect(planoContabilService.comparePlanoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
