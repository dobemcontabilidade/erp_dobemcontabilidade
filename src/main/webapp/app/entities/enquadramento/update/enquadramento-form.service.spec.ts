import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../enquadramento.test-samples';

import { EnquadramentoFormService } from './enquadramento-form.service';

describe('Enquadramento Form Service', () => {
  let service: EnquadramentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnquadramentoFormService);
  });

  describe('Service methods', () => {
    describe('createEnquadramentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnquadramentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            sigla: expect.any(Object),
            limiteInicial: expect.any(Object),
            limiteFinal: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IEnquadramento should create a new form with FormGroup', () => {
        const formGroup = service.createEnquadramentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            sigla: expect.any(Object),
            limiteInicial: expect.any(Object),
            limiteFinal: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getEnquadramento', () => {
      it('should return NewEnquadramento for default Enquadramento initial value', () => {
        const formGroup = service.createEnquadramentoFormGroup(sampleWithNewData);

        const enquadramento = service.getEnquadramento(formGroup) as any;

        expect(enquadramento).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnquadramento for empty Enquadramento initial value', () => {
        const formGroup = service.createEnquadramentoFormGroup();

        const enquadramento = service.getEnquadramento(formGroup) as any;

        expect(enquadramento).toMatchObject({});
      });

      it('should return IEnquadramento', () => {
        const formGroup = service.createEnquadramentoFormGroup(sampleWithRequiredData);

        const enquadramento = service.getEnquadramento(formGroup) as any;

        expect(enquadramento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnquadramento should not enable id FormControl', () => {
        const formGroup = service.createEnquadramentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnquadramento should disable id FormControl', () => {
        const formGroup = service.createEnquadramentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
