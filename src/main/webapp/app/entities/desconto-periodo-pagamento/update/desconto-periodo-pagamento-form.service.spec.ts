import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../desconto-periodo-pagamento.test-samples';

import { DescontoPeriodoPagamentoFormService } from './desconto-periodo-pagamento-form.service';

describe('DescontoPeriodoPagamento Form Service', () => {
  let service: DescontoPeriodoPagamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DescontoPeriodoPagamentoFormService);
  });

  describe('Service methods', () => {
    describe('createDescontoPeriodoPagamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            periodoPagamento: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });

      it('passing IDescontoPeriodoPagamento should create a new form with FormGroup', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            periodoPagamento: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getDescontoPeriodoPagamento', () => {
      it('should return NewDescontoPeriodoPagamento for default DescontoPeriodoPagamento initial value', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup(sampleWithNewData);

        const descontoPeriodoPagamento = service.getDescontoPeriodoPagamento(formGroup) as any;

        expect(descontoPeriodoPagamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewDescontoPeriodoPagamento for empty DescontoPeriodoPagamento initial value', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup();

        const descontoPeriodoPagamento = service.getDescontoPeriodoPagamento(formGroup) as any;

        expect(descontoPeriodoPagamento).toMatchObject({});
      });

      it('should return IDescontoPeriodoPagamento', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup(sampleWithRequiredData);

        const descontoPeriodoPagamento = service.getDescontoPeriodoPagamento(formGroup) as any;

        expect(descontoPeriodoPagamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDescontoPeriodoPagamento should not enable id FormControl', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDescontoPeriodoPagamento should disable id FormControl', () => {
        const formGroup = service.createDescontoPeriodoPagamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
