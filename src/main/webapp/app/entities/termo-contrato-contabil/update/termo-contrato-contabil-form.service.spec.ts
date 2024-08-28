import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../termo-contrato-contabil.test-samples';

import { TermoContratoContabilFormService } from './termo-contrato-contabil-form.service';

describe('TermoContratoContabil Form Service', () => {
  let service: TermoContratoContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermoContratoContabilFormService);
  });

  describe('Service methods', () => {
    describe('createTermoContratoContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTermoContratoContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descricao: expect.any(Object),
            urlDocumentoFonte: expect.any(Object),
            documento: expect.any(Object),
            disponivel: expect.any(Object),
            dataCriacao: expect.any(Object),
          }),
        );
      });

      it('passing ITermoContratoContabil should create a new form with FormGroup', () => {
        const formGroup = service.createTermoContratoContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descricao: expect.any(Object),
            urlDocumentoFonte: expect.any(Object),
            documento: expect.any(Object),
            disponivel: expect.any(Object),
            dataCriacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getTermoContratoContabil', () => {
      it('should return NewTermoContratoContabil for default TermoContratoContabil initial value', () => {
        const formGroup = service.createTermoContratoContabilFormGroup(sampleWithNewData);

        const termoContratoContabil = service.getTermoContratoContabil(formGroup) as any;

        expect(termoContratoContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewTermoContratoContabil for empty TermoContratoContabil initial value', () => {
        const formGroup = service.createTermoContratoContabilFormGroup();

        const termoContratoContabil = service.getTermoContratoContabil(formGroup) as any;

        expect(termoContratoContabil).toMatchObject({});
      });

      it('should return ITermoContratoContabil', () => {
        const formGroup = service.createTermoContratoContabilFormGroup(sampleWithRequiredData);

        const termoContratoContabil = service.getTermoContratoContabil(formGroup) as any;

        expect(termoContratoContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITermoContratoContabil should not enable id FormControl', () => {
        const formGroup = service.createTermoContratoContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTermoContratoContabil should disable id FormControl', () => {
        const formGroup = service.createTermoContratoContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
