import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../termo-contrato-assinatura-empresa.test-samples';

import { TermoContratoAssinaturaEmpresaFormService } from './termo-contrato-assinatura-empresa-form.service';

describe('TermoContratoAssinaturaEmpresa Form Service', () => {
  let service: TermoContratoAssinaturaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermoContratoAssinaturaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createTermoContratoAssinaturaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAssinatura: expect.any(Object),
            dataEnvioEmail: expect.any(Object),
            urlDocumentoAssinado: expect.any(Object),
            situacao: expect.any(Object),
            termoContratoContabil: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing ITermoContratoAssinaturaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAssinatura: expect.any(Object),
            dataEnvioEmail: expect.any(Object),
            urlDocumentoAssinado: expect.any(Object),
            situacao: expect.any(Object),
            termoContratoContabil: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getTermoContratoAssinaturaEmpresa', () => {
      it('should return NewTermoContratoAssinaturaEmpresa for default TermoContratoAssinaturaEmpresa initial value', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup(sampleWithNewData);

        const termoContratoAssinaturaEmpresa = service.getTermoContratoAssinaturaEmpresa(formGroup) as any;

        expect(termoContratoAssinaturaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewTermoContratoAssinaturaEmpresa for empty TermoContratoAssinaturaEmpresa initial value', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup();

        const termoContratoAssinaturaEmpresa = service.getTermoContratoAssinaturaEmpresa(formGroup) as any;

        expect(termoContratoAssinaturaEmpresa).toMatchObject({});
      });

      it('should return ITermoContratoAssinaturaEmpresa', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        const termoContratoAssinaturaEmpresa = service.getTermoContratoAssinaturaEmpresa(formGroup) as any;

        expect(termoContratoAssinaturaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITermoContratoAssinaturaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTermoContratoAssinaturaEmpresa should disable id FormControl', () => {
        const formGroup = service.createTermoContratoAssinaturaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
