import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../certificado-digital.test-samples';

import { CertificadoDigitalFormService } from './certificado-digital-form.service';

describe('CertificadoDigital Form Service', () => {
  let service: CertificadoDigitalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CertificadoDigitalFormService);
  });

  describe('Service methods', () => {
    describe('createCertificadoDigitalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCertificadoDigitalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            sigla: expect.any(Object),
            descricao: expect.any(Object),
            tipoCertificado: expect.any(Object),
          }),
        );
      });

      it('passing ICertificadoDigital should create a new form with FormGroup', () => {
        const formGroup = service.createCertificadoDigitalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            sigla: expect.any(Object),
            descricao: expect.any(Object),
            tipoCertificado: expect.any(Object),
          }),
        );
      });
    });

    describe('getCertificadoDigital', () => {
      it('should return NewCertificadoDigital for default CertificadoDigital initial value', () => {
        const formGroup = service.createCertificadoDigitalFormGroup(sampleWithNewData);

        const certificadoDigital = service.getCertificadoDigital(formGroup) as any;

        expect(certificadoDigital).toMatchObject(sampleWithNewData);
      });

      it('should return NewCertificadoDigital for empty CertificadoDigital initial value', () => {
        const formGroup = service.createCertificadoDigitalFormGroup();

        const certificadoDigital = service.getCertificadoDigital(formGroup) as any;

        expect(certificadoDigital).toMatchObject({});
      });

      it('should return ICertificadoDigital', () => {
        const formGroup = service.createCertificadoDigitalFormGroup(sampleWithRequiredData);

        const certificadoDigital = service.getCertificadoDigital(formGroup) as any;

        expect(certificadoDigital).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICertificadoDigital should not enable id FormControl', () => {
        const formGroup = service.createCertificadoDigitalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCertificadoDigital should disable id FormControl', () => {
        const formGroup = service.createCertificadoDigitalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
