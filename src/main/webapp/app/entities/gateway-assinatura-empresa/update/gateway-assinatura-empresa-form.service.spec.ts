import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../gateway-assinatura-empresa.test-samples';

import { GatewayAssinaturaEmpresaFormService } from './gateway-assinatura-empresa-form.service';

describe('GatewayAssinaturaEmpresa Form Service', () => {
  let service: GatewayAssinaturaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GatewayAssinaturaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createGatewayAssinaturaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            codigoAssinatura: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            gatewayPagamento: expect.any(Object),
          }),
        );
      });

      it('passing IGatewayAssinaturaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            codigoAssinatura: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            gatewayPagamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getGatewayAssinaturaEmpresa', () => {
      it('should return NewGatewayAssinaturaEmpresa for default GatewayAssinaturaEmpresa initial value', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup(sampleWithNewData);

        const gatewayAssinaturaEmpresa = service.getGatewayAssinaturaEmpresa(formGroup) as any;

        expect(gatewayAssinaturaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewGatewayAssinaturaEmpresa for empty GatewayAssinaturaEmpresa initial value', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup();

        const gatewayAssinaturaEmpresa = service.getGatewayAssinaturaEmpresa(formGroup) as any;

        expect(gatewayAssinaturaEmpresa).toMatchObject({});
      });

      it('should return IGatewayAssinaturaEmpresa', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        const gatewayAssinaturaEmpresa = service.getGatewayAssinaturaEmpresa(formGroup) as any;

        expect(gatewayAssinaturaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGatewayAssinaturaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGatewayAssinaturaEmpresa should disable id FormControl', () => {
        const formGroup = service.createGatewayAssinaturaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
