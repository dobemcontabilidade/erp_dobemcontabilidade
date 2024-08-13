import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-contabil.test-samples';

import { AreaContabilFormService } from './area-contabil-form.service';

describe('AreaContabil Form Service', () => {
  let service: AreaContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaContabilFormService);
  });

  describe('Service methods', () => {
    describe('createAreaContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            percentual: expect.any(Object),
          }),
        );
      });

      it('passing IAreaContabil should create a new form with FormGroup', () => {
        const formGroup = service.createAreaContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            percentual: expect.any(Object),
          }),
        );
      });
    });

    describe('getAreaContabil', () => {
      it('should return NewAreaContabil for default AreaContabil initial value', () => {
        const formGroup = service.createAreaContabilFormGroup(sampleWithNewData);

        const areaContabil = service.getAreaContabil(formGroup) as any;

        expect(areaContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaContabil for empty AreaContabil initial value', () => {
        const formGroup = service.createAreaContabilFormGroup();

        const areaContabil = service.getAreaContabil(formGroup) as any;

        expect(areaContabil).toMatchObject({});
      });

      it('should return IAreaContabil', () => {
        const formGroup = service.createAreaContabilFormGroup(sampleWithRequiredData);

        const areaContabil = service.getAreaContabil(formGroup) as any;

        expect(areaContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaContabil should not enable id FormControl', () => {
        const formGroup = service.createAreaContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAreaContabil should disable id FormControl', () => {
        const formGroup = service.createAreaContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
