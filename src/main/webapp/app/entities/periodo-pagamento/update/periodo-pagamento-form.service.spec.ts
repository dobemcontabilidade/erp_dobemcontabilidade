import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../periodo-pagamento.test-samples';

import { PeriodoPagamentoFormService } from './periodo-pagamento-form.service';

describe('PeriodoPagamento Form Service', () => {
  let service: PeriodoPagamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeriodoPagamentoFormService);
  });

  describe('Service methods', () => {
    describe('createPeriodoPagamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            periodo: expect.any(Object),
            numeroDias: expect.any(Object),
            idPlanGnet: expect.any(Object),
          }),
        );
      });

      it('passing IPeriodoPagamento should create a new form with FormGroup', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            periodo: expect.any(Object),
            numeroDias: expect.any(Object),
            idPlanGnet: expect.any(Object),
          }),
        );
      });
    });

    describe('getPeriodoPagamento', () => {
      it('should return NewPeriodoPagamento for default PeriodoPagamento initial value', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup(sampleWithNewData);

        const periodoPagamento = service.getPeriodoPagamento(formGroup) as any;

        expect(periodoPagamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewPeriodoPagamento for empty PeriodoPagamento initial value', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup();

        const periodoPagamento = service.getPeriodoPagamento(formGroup) as any;

        expect(periodoPagamento).toMatchObject({});
      });

      it('should return IPeriodoPagamento', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup(sampleWithRequiredData);

        const periodoPagamento = service.getPeriodoPagamento(formGroup) as any;

        expect(periodoPagamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPeriodoPagamento should not enable id FormControl', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPeriodoPagamento should disable id FormControl', () => {
        const formGroup = service.createPeriodoPagamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
