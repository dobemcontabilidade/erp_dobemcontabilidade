import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PeriodoPagamentoService } from '../service/periodo-pagamento.service';
import { IPeriodoPagamento } from '../periodo-pagamento.model';
import { PeriodoPagamentoFormService } from './periodo-pagamento-form.service';

import { PeriodoPagamentoUpdateComponent } from './periodo-pagamento-update.component';

describe('PeriodoPagamento Management Update Component', () => {
  let comp: PeriodoPagamentoUpdateComponent;
  let fixture: ComponentFixture<PeriodoPagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let periodoPagamentoFormService: PeriodoPagamentoFormService;
  let periodoPagamentoService: PeriodoPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PeriodoPagamentoUpdateComponent],
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
      .overrideTemplate(PeriodoPagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PeriodoPagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    periodoPagamentoFormService = TestBed.inject(PeriodoPagamentoFormService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const periodoPagamento: IPeriodoPagamento = { id: 456 };

      activatedRoute.data = of({ periodoPagamento });
      comp.ngOnInit();

      expect(comp.periodoPagamento).toEqual(periodoPagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoPagamento>>();
      const periodoPagamento = { id: 123 };
      jest.spyOn(periodoPagamentoFormService, 'getPeriodoPagamento').mockReturnValue(periodoPagamento);
      jest.spyOn(periodoPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoPagamento }));
      saveSubject.complete();

      // THEN
      expect(periodoPagamentoFormService.getPeriodoPagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(periodoPagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(periodoPagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoPagamento>>();
      const periodoPagamento = { id: 123 };
      jest.spyOn(periodoPagamentoFormService, 'getPeriodoPagamento').mockReturnValue({ id: null });
      jest.spyOn(periodoPagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoPagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoPagamento }));
      saveSubject.complete();

      // THEN
      expect(periodoPagamentoFormService.getPeriodoPagamento).toHaveBeenCalled();
      expect(periodoPagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoPagamento>>();
      const periodoPagamento = { id: 123 };
      jest.spyOn(periodoPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(periodoPagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
