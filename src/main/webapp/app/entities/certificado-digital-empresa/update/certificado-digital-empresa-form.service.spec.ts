import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../certificado-digital-empresa.test-samples';

import { CertificadoDigitalEmpresaFormService } from './certificado-digital-empresa-form.service';

describe('CertificadoDigitalEmpresa Form Service', () => {
  let service: CertificadoDigitalEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CertificadoDigitalEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createCertificadoDigitalEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlCertificado: expect.any(Object),
            dataContratacao: expect.any(Object),
            dataVencimento: expect.any(Object),
            diasUso: expect.any(Object),
            pessoaJuridica: expect.any(Object),
            certificadoDigital: expect.any(Object),
            fornecedorCertificado: expect.any(Object),
          }),
        );
      });

      it('passing ICertificadoDigitalEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlCertificado: expect.any(Object),
            dataContratacao: expect.any(Object),
            dataVencimento: expect.any(Object),
            diasUso: expect.any(Object),
            pessoaJuridica: expect.any(Object),
            certificadoDigital: expect.any(Object),
            fornecedorCertificado: expect.any(Object),
          }),
        );
      });
    });

    describe('getCertificadoDigitalEmpresa', () => {
      it('should return NewCertificadoDigitalEmpresa for default CertificadoDigitalEmpresa initial value', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup(sampleWithNewData);

        const certificadoDigitalEmpresa = service.getCertificadoDigitalEmpresa(formGroup) as any;

        expect(certificadoDigitalEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewCertificadoDigitalEmpresa for empty CertificadoDigitalEmpresa initial value', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup();

        const certificadoDigitalEmpresa = service.getCertificadoDigitalEmpresa(formGroup) as any;

        expect(certificadoDigitalEmpresa).toMatchObject({});
      });

      it('should return ICertificadoDigitalEmpresa', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup(sampleWithRequiredData);

        const certificadoDigitalEmpresa = service.getCertificadoDigitalEmpresa(formGroup) as any;

        expect(certificadoDigitalEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICertificadoDigitalEmpresa should not enable id FormControl', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCertificadoDigitalEmpresa should disable id FormControl', () => {
        const formGroup = service.createCertificadoDigitalEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
