import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../adicional-enquadramento.test-samples';

import { AdicionalEnquadramentoFormService } from './adicional-enquadramento-form.service';

describe('AdicionalEnquadramento Form Service', () => {
  let service: AdicionalEnquadramentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdicionalEnquadramentoFormService);
  });

  describe('Service methods', () => {
    describe('createAdicionalEnquadramentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            enquadramento: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });

      it('passing IAdicionalEnquadramento should create a new form with FormGroup', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            enquadramento: expect.any(Object),
            planoAssinaturaContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getAdicionalEnquadramento', () => {
      it('should return NewAdicionalEnquadramento for default AdicionalEnquadramento initial value', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup(sampleWithNewData);

        const adicionalEnquadramento = service.getAdicionalEnquadramento(formGroup) as any;

        expect(adicionalEnquadramento).toMatchObject(sampleWithNewData);
      });

      it('should return NewAdicionalEnquadramento for empty AdicionalEnquadramento initial value', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup();

        const adicionalEnquadramento = service.getAdicionalEnquadramento(formGroup) as any;

        expect(adicionalEnquadramento).toMatchObject({});
      });

      it('should return IAdicionalEnquadramento', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup(sampleWithRequiredData);

        const adicionalEnquadramento = service.getAdicionalEnquadramento(formGroup) as any;

        expect(adicionalEnquadramento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAdicionalEnquadramento should not enable id FormControl', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAdicionalEnquadramento should disable id FormControl', () => {
        const formGroup = service.createAdicionalEnquadramentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
