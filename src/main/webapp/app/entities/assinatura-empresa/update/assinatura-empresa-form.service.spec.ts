import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../assinatura-empresa.test-samples';

import { AssinaturaEmpresaFormService } from './assinatura-empresa-form.service';

describe('AssinaturaEmpresa Form Service', () => {
  let service: AssinaturaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssinaturaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAssinaturaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            codigoAssinatura: expect.any(Object),
            valorEnquadramento: expect.any(Object),
            valorTributacao: expect.any(Object),
            valorRamo: expect.any(Object),
            valorFuncionarios: expect.any(Object),
            valorSocios: expect.any(Object),
            valorFaturamento: expect.any(Object),
            valorPlanoContabil: expect.any(Object),
            valorPlanoContabilComDesconto: expect.any(Object),
            valorPlanoContaAzulComDesconto: expect.any(Object),
            valorMensalidade: expect.any(Object),
            valorPeriodo: expect.any(Object),
            valorAno: expect.any(Object),
            dataContratacao: expect.any(Object),
            dataEncerramento: expect.any(Object),
            diaVencimento: expect.any(Object),
            situacao: expect.any(Object),
            tipoContrato: expect.any(Object),
            periodoPagamento: expect.any(Object),
            formaDePagamento: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing IAssinaturaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            codigoAssinatura: expect.any(Object),
            valorEnquadramento: expect.any(Object),
            valorTributacao: expect.any(Object),
            valorRamo: expect.any(Object),
            valorFuncionarios: expect.any(Object),
            valorSocios: expect.any(Object),
            valorFaturamento: expect.any(Object),
            valorPlanoContabil: expect.any(Object),
            valorPlanoContabilComDesconto: expect.any(Object),
            valorPlanoContaAzulComDesconto: expect.any(Object),
            valorMensalidade: expect.any(Object),
            valorPeriodo: expect.any(Object),
            valorAno: expect.any(Object),
            dataContratacao: expect.any(Object),
            dataEncerramento: expect.any(Object),
            diaVencimento: expect.any(Object),
            situacao: expect.any(Object),
            tipoContrato: expect.any(Object),
            periodoPagamento: expect.any(Object),
            formaDePagamento: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getAssinaturaEmpresa', () => {
      it('should return NewAssinaturaEmpresa for default AssinaturaEmpresa initial value', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup(sampleWithNewData);

        const assinaturaEmpresa = service.getAssinaturaEmpresa(formGroup) as any;

        expect(assinaturaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssinaturaEmpresa for empty AssinaturaEmpresa initial value', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup();

        const assinaturaEmpresa = service.getAssinaturaEmpresa(formGroup) as any;

        expect(assinaturaEmpresa).toMatchObject({});
      });

      it('should return IAssinaturaEmpresa', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        const assinaturaEmpresa = service.getAssinaturaEmpresa(formGroup) as any;

        expect(assinaturaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssinaturaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssinaturaEmpresa should disable id FormControl', () => {
        const formGroup = service.createAssinaturaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
