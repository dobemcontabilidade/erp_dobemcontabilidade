import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/service/plano-conta-azul.service';
import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';
import { DescontoPlanoContaAzulService } from '../service/desconto-plano-conta-azul.service';
import { DescontoPlanoContaAzulFormService } from './desconto-plano-conta-azul-form.service';

import { DescontoPlanoContaAzulUpdateComponent } from './desconto-plano-conta-azul-update.component';

describe('DescontoPlanoContaAzul Management Update Component', () => {
  let comp: DescontoPlanoContaAzulUpdateComponent;
  let fixture: ComponentFixture<DescontoPlanoContaAzulUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let descontoPlanoContaAzulFormService: DescontoPlanoContaAzulFormService;
  let descontoPlanoContaAzulService: DescontoPlanoContaAzulService;
  let planoContaAzulService: PlanoContaAzulService;
  let periodoPagamentoService: PeriodoPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DescontoPlanoContaAzulUpdateComponent],
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
      .overrideTemplate(DescontoPlanoContaAzulUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DescontoPlanoContaAzulUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    descontoPlanoContaAzulFormService = TestBed.inject(DescontoPlanoContaAzulFormService);
    descontoPlanoContaAzulService = TestBed.inject(DescontoPlanoContaAzulService);
    planoContaAzulService = TestBed.inject(PlanoContaAzulService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlanoContaAzul query and add missing value', () => {
      const descontoPlanoContaAzul: IDescontoPlanoContaAzul = { id: 456 };
      const planoContaAzul: IPlanoContaAzul = { id: 21289 };
      descontoPlanoContaAzul.planoContaAzul = planoContaAzul;

      const planoContaAzulCollection: IPlanoContaAzul[] = [{ id: 10671 }];
      jest.spyOn(planoContaAzulService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContaAzulCollection })));
      const additionalPlanoContaAzuls = [planoContaAzul];
      const expectedCollection: IPlanoContaAzul[] = [...additionalPlanoContaAzuls, ...planoContaAzulCollection];
      jest.spyOn(planoContaAzulService, 'addPlanoContaAzulToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPlanoContaAzul });
      comp.ngOnInit();

      expect(planoContaAzulService.query).toHaveBeenCalled();
      expect(planoContaAzulService.addPlanoContaAzulToCollectionIfMissing).toHaveBeenCalledWith(
        planoContaAzulCollection,
        ...additionalPlanoContaAzuls.map(expect.objectContaining),
      );
      expect(comp.planoContaAzulsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PeriodoPagamento query and add missing value', () => {
      const descontoPlanoContaAzul: IDescontoPlanoContaAzul = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 26471 };
      descontoPlanoContaAzul.periodoPagamento = periodoPagamento;

      const periodoPagamentoCollection: IPeriodoPagamento[] = [{ id: 1598 }];
      jest.spyOn(periodoPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoPagamentoCollection })));
      const additionalPeriodoPagamentos = [periodoPagamento];
      const expectedCollection: IPeriodoPagamento[] = [...additionalPeriodoPagamentos, ...periodoPagamentoCollection];
      jest.spyOn(periodoPagamentoService, 'addPeriodoPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ descontoPlanoContaAzul });
      comp.ngOnInit();

      expect(periodoPagamentoService.query).toHaveBeenCalled();
      expect(periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoPagamentoCollection,
        ...additionalPeriodoPagamentos.map(expect.objectContaining),
      );
      expect(comp.periodoPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const descontoPlanoContaAzul: IDescontoPlanoContaAzul = { id: 456 };
      const planoContaAzul: IPlanoContaAzul = { id: 12082 };
      descontoPlanoContaAzul.planoContaAzul = planoContaAzul;
      const periodoPagamento: IPeriodoPagamento = { id: 15434 };
      descontoPlanoContaAzul.periodoPagamento = periodoPagamento;

      activatedRoute.data = of({ descontoPlanoContaAzul });
      comp.ngOnInit();

      expect(comp.planoContaAzulsSharedCollection).toContain(planoContaAzul);
      expect(comp.periodoPagamentosSharedCollection).toContain(periodoPagamento);
      expect(comp.descontoPlanoContaAzul).toEqual(descontoPlanoContaAzul);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContaAzul>>();
      const descontoPlanoContaAzul = { id: 123 };
      jest.spyOn(descontoPlanoContaAzulFormService, 'getDescontoPlanoContaAzul').mockReturnValue(descontoPlanoContaAzul);
      jest.spyOn(descontoPlanoContaAzulService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContaAzul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPlanoContaAzul }));
      saveSubject.complete();

      // THEN
      expect(descontoPlanoContaAzulFormService.getDescontoPlanoContaAzul).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(descontoPlanoContaAzulService.update).toHaveBeenCalledWith(expect.objectContaining(descontoPlanoContaAzul));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContaAzul>>();
      const descontoPlanoContaAzul = { id: 123 };
      jest.spyOn(descontoPlanoContaAzulFormService, 'getDescontoPlanoContaAzul').mockReturnValue({ id: null });
      jest.spyOn(descontoPlanoContaAzulService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContaAzul: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: descontoPlanoContaAzul }));
      saveSubject.complete();

      // THEN
      expect(descontoPlanoContaAzulFormService.getDescontoPlanoContaAzul).toHaveBeenCalled();
      expect(descontoPlanoContaAzulService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDescontoPlanoContaAzul>>();
      const descontoPlanoContaAzul = { id: 123 };
      jest.spyOn(descontoPlanoContaAzulService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ descontoPlanoContaAzul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(descontoPlanoContaAzulService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlanoContaAzul', () => {
      it('Should forward to planoContaAzulService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContaAzulService, 'comparePlanoContaAzul');
        comp.comparePlanoContaAzul(entity, entity2);
        expect(planoContaAzulService.comparePlanoContaAzul).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePeriodoPagamento', () => {
      it('Should forward to periodoPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoPagamentoService, 'comparePeriodoPagamento');
        comp.comparePeriodoPagamento(entity, entity2);
        expect(periodoPagamentoService.comparePeriodoPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
