import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../calculo-plano-assinatura.test-samples';

import { CalculoPlanoAssinaturaFormService } from './calculo-plano-assinatura-form.service';

describe('CalculoPlanoAssinatura Form Service', () => {
  let service: CalculoPlanoAssinaturaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CalculoPlanoAssinaturaFormService);
  });

  describe('Service methods', () => {
    describe('createCalculoPlanoAssinaturaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigoAtendimento: expect.any(Object),
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
            periodoPagamento: expect.any(Object),
            planoContaAzul: expect.any(Object),
            planoContabil: expect.any(Object),
            ramo: expect.any(Object),
            tributacao: expect.any(Object),
            descontoPlanoContabil: expect.any(Object),
            descontoPlanoContaAzul: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing ICalculoPlanoAssinatura should create a new form with FormGroup', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigoAtendimento: expect.any(Object),
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
            periodoPagamento: expect.any(Object),
            planoContaAzul: expect.any(Object),
            planoContabil: expect.any(Object),
            ramo: expect.any(Object),
            tributacao: expect.any(Object),
            descontoPlanoContabil: expect.any(Object),
            descontoPlanoContaAzul: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getCalculoPlanoAssinatura', () => {
      it('should return NewCalculoPlanoAssinatura for default CalculoPlanoAssinatura initial value', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup(sampleWithNewData);

        const calculoPlanoAssinatura = service.getCalculoPlanoAssinatura(formGroup) as any;

        expect(calculoPlanoAssinatura).toMatchObject(sampleWithNewData);
      });

      it('should return NewCalculoPlanoAssinatura for empty CalculoPlanoAssinatura initial value', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup();

        const calculoPlanoAssinatura = service.getCalculoPlanoAssinatura(formGroup) as any;

        expect(calculoPlanoAssinatura).toMatchObject({});
      });

      it('should return ICalculoPlanoAssinatura', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup(sampleWithRequiredData);

        const calculoPlanoAssinatura = service.getCalculoPlanoAssinatura(formGroup) as any;

        expect(calculoPlanoAssinatura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICalculoPlanoAssinatura should not enable id FormControl', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCalculoPlanoAssinatura should disable id FormControl', () => {
        const formGroup = service.createCalculoPlanoAssinaturaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
