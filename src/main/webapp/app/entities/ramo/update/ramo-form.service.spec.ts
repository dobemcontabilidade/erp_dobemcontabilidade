import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ramo.test-samples';

import { RamoFormService } from './ramo-form.service';

describe('Ramo Form Service', () => {
  let service: RamoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RamoFormService);
  });

  describe('Service methods', () => {
    describe('createRamoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRamoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IRamo should create a new form with FormGroup', () => {
        const formGroup = service.createRamoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getRamo', () => {
      it('should return NewRamo for default Ramo initial value', () => {
        const formGroup = service.createRamoFormGroup(sampleWithNewData);

        const ramo = service.getRamo(formGroup) as any;

        expect(ramo).toMatchObject(sampleWithNewData);
      });

      it('should return NewRamo for empty Ramo initial value', () => {
        const formGroup = service.createRamoFormGroup();

        const ramo = service.getRamo(formGroup) as any;

        expect(ramo).toMatchObject({});
      });

      it('should return IRamo', () => {
        const formGroup = service.createRamoFormGroup(sampleWithRequiredData);

        const ramo = service.getRamo(formGroup) as any;

        expect(ramo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRamo should not enable id FormControl', () => {
        const formGroup = service.createRamoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRamo should disable id FormControl', () => {
        const formGroup = service.createRamoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
