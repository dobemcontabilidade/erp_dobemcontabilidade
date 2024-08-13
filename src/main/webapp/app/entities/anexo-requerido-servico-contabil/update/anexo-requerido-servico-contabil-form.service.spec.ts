import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido-servico-contabil.test-samples';

import { AnexoRequeridoServicoContabilFormService } from './anexo-requerido-servico-contabil-form.service';

describe('AnexoRequeridoServicoContabil Form Service', () => {
  let service: AnexoRequeridoServicoContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoServicoContabilFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoServicoContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            servicoContabil: expect.any(Object),
            anexoRequerido: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequeridoServicoContabil should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            servicoContabil: expect.any(Object),
            anexoRequerido: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequeridoServicoContabil', () => {
      it('should return NewAnexoRequeridoServicoContabil for default AnexoRequeridoServicoContabil initial value', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup(sampleWithNewData);

        const anexoRequeridoServicoContabil = service.getAnexoRequeridoServicoContabil(formGroup) as any;

        expect(anexoRequeridoServicoContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequeridoServicoContabil for empty AnexoRequeridoServicoContabil initial value', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup();

        const anexoRequeridoServicoContabil = service.getAnexoRequeridoServicoContabil(formGroup) as any;

        expect(anexoRequeridoServicoContabil).toMatchObject({});
      });

      it('should return IAnexoRequeridoServicoContabil', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup(sampleWithRequiredData);

        const anexoRequeridoServicoContabil = service.getAnexoRequeridoServicoContabil(formGroup) as any;

        expect(anexoRequeridoServicoContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequeridoServicoContabil should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequeridoServicoContabil should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoServicoContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
