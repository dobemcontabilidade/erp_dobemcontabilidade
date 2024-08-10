import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-contador-area-contabil.test-samples';

import { PerfilContadorAreaContabilFormService } from './perfil-contador-area-contabil-form.service';

describe('PerfilContadorAreaContabil Form Service', () => {
  let service: PerfilContadorAreaContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilContadorAreaContabilFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilContadorAreaContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidadeEmpresas: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            areaContabil: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilContadorAreaContabil should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidadeEmpresas: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            areaContabil: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilContadorAreaContabil', () => {
      it('should return NewPerfilContadorAreaContabil for default PerfilContadorAreaContabil initial value', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup(sampleWithNewData);

        const perfilContadorAreaContabil = service.getPerfilContadorAreaContabil(formGroup) as any;

        expect(perfilContadorAreaContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilContadorAreaContabil for empty PerfilContadorAreaContabil initial value', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup();

        const perfilContadorAreaContabil = service.getPerfilContadorAreaContabil(formGroup) as any;

        expect(perfilContadorAreaContabil).toMatchObject({});
      });

      it('should return IPerfilContadorAreaContabil', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup(sampleWithRequiredData);

        const perfilContadorAreaContabil = service.getPerfilContadorAreaContabil(formGroup) as any;

        expect(perfilContadorAreaContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilContadorAreaContabil should not enable id FormControl', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilContadorAreaContabil should disable id FormControl', () => {
        const formGroup = service.createPerfilContadorAreaContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
