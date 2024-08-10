import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../forma-de-pagamento.test-samples';

import { FormaDePagamentoFormService } from './forma-de-pagamento-form.service';

describe('FormaDePagamento Form Service', () => {
  let service: FormaDePagamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormaDePagamentoFormService);
  });

  describe('Service methods', () => {
    describe('createFormaDePagamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormaDePagamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            forma: expect.any(Object),
            disponivel: expect.any(Object),
          }),
        );
      });

      it('passing IFormaDePagamento should create a new form with FormGroup', () => {
        const formGroup = service.createFormaDePagamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            forma: expect.any(Object),
            disponivel: expect.any(Object),
          }),
        );
      });
    });

    describe('getFormaDePagamento', () => {
      it('should return NewFormaDePagamento for default FormaDePagamento initial value', () => {
        const formGroup = service.createFormaDePagamentoFormGroup(sampleWithNewData);

        const formaDePagamento = service.getFormaDePagamento(formGroup) as any;

        expect(formaDePagamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormaDePagamento for empty FormaDePagamento initial value', () => {
        const formGroup = service.createFormaDePagamentoFormGroup();

        const formaDePagamento = service.getFormaDePagamento(formGroup) as any;

        expect(formaDePagamento).toMatchObject({});
      });

      it('should return IFormaDePagamento', () => {
        const formGroup = service.createFormaDePagamentoFormGroup(sampleWithRequiredData);

        const formaDePagamento = service.getFormaDePagamento(formGroup) as any;

        expect(formaDePagamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormaDePagamento should not enable id FormControl', () => {
        const formGroup = service.createFormaDePagamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormaDePagamento should disable id FormControl', () => {
        const formGroup = service.createFormaDePagamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
