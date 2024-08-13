import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../gateway-pagamento.test-samples';

import { GatewayPagamentoFormService } from './gateway-pagamento-form.service';

describe('GatewayPagamento Form Service', () => {
  let service: GatewayPagamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GatewayPagamentoFormService);
  });

  describe('Service methods', () => {
    describe('createGatewayPagamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGatewayPagamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IGatewayPagamento should create a new form with FormGroup', () => {
        const formGroup = service.createGatewayPagamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getGatewayPagamento', () => {
      it('should return NewGatewayPagamento for default GatewayPagamento initial value', () => {
        const formGroup = service.createGatewayPagamentoFormGroup(sampleWithNewData);

        const gatewayPagamento = service.getGatewayPagamento(formGroup) as any;

        expect(gatewayPagamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewGatewayPagamento for empty GatewayPagamento initial value', () => {
        const formGroup = service.createGatewayPagamentoFormGroup();

        const gatewayPagamento = service.getGatewayPagamento(formGroup) as any;

        expect(gatewayPagamento).toMatchObject({});
      });

      it('should return IGatewayPagamento', () => {
        const formGroup = service.createGatewayPagamentoFormGroup(sampleWithRequiredData);

        const gatewayPagamento = service.getGatewayPagamento(formGroup) as any;

        expect(gatewayPagamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGatewayPagamento should not enable id FormControl', () => {
        const formGroup = service.createGatewayPagamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGatewayPagamento should disable id FormControl', () => {
        const formGroup = service.createGatewayPagamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
