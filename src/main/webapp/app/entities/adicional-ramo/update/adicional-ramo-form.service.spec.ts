import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../adicional-ramo.test-samples';

import { AdicionalRamoFormService } from './adicional-ramo-form.service';

describe('AdicionalRamo Form Service', () => {
  let service: AdicionalRamoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdicionalRamoFormService);
  });

  describe('Service methods', () => {
    describe('createAdicionalRamoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAdicionalRamoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            ramo: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });

      it('passing IAdicionalRamo should create a new form with FormGroup', () => {
        const formGroup = service.createAdicionalRamoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            ramo: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getAdicionalRamo', () => {
      it('should return NewAdicionalRamo for default AdicionalRamo initial value', () => {
        const formGroup = service.createAdicionalRamoFormGroup(sampleWithNewData);

        const adicionalRamo = service.getAdicionalRamo(formGroup) as any;

        expect(adicionalRamo).toMatchObject(sampleWithNewData);
      });

      it('should return NewAdicionalRamo for empty AdicionalRamo initial value', () => {
        const formGroup = service.createAdicionalRamoFormGroup();

        const adicionalRamo = service.getAdicionalRamo(formGroup) as any;

        expect(adicionalRamo).toMatchObject({});
      });

      it('should return IAdicionalRamo', () => {
        const formGroup = service.createAdicionalRamoFormGroup(sampleWithRequiredData);

        const adicionalRamo = service.getAdicionalRamo(formGroup) as any;

        expect(adicionalRamo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAdicionalRamo should not enable id FormControl', () => {
        const formGroup = service.createAdicionalRamoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAdicionalRamo should disable id FormControl', () => {
        const formGroup = service.createAdicionalRamoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
