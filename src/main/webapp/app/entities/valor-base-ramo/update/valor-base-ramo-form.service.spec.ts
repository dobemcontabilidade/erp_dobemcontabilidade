import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../valor-base-ramo.test-samples';

import { ValorBaseRamoFormService } from './valor-base-ramo-form.service';

describe('ValorBaseRamo Form Service', () => {
  let service: ValorBaseRamoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValorBaseRamoFormService);
  });

  describe('Service methods', () => {
    describe('createValorBaseRamoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createValorBaseRamoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorBase: expect.any(Object),
            ramo: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });

      it('passing IValorBaseRamo should create a new form with FormGroup', () => {
        const formGroup = service.createValorBaseRamoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorBase: expect.any(Object),
            ramo: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getValorBaseRamo', () => {
      it('should return NewValorBaseRamo for default ValorBaseRamo initial value', () => {
        const formGroup = service.createValorBaseRamoFormGroup(sampleWithNewData);

        const valorBaseRamo = service.getValorBaseRamo(formGroup) as any;

        expect(valorBaseRamo).toMatchObject(sampleWithNewData);
      });

      it('should return NewValorBaseRamo for empty ValorBaseRamo initial value', () => {
        const formGroup = service.createValorBaseRamoFormGroup();

        const valorBaseRamo = service.getValorBaseRamo(formGroup) as any;

        expect(valorBaseRamo).toMatchObject({});
      });

      it('should return IValorBaseRamo', () => {
        const formGroup = service.createValorBaseRamoFormGroup(sampleWithRequiredData);

        const valorBaseRamo = service.getValorBaseRamo(formGroup) as any;

        expect(valorBaseRamo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IValorBaseRamo should not enable id FormControl', () => {
        const formGroup = service.createValorBaseRamoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewValorBaseRamo should disable id FormControl', () => {
        const formGroup = service.createValorBaseRamoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
