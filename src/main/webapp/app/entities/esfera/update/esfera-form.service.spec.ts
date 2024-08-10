import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../esfera.test-samples';

import { EsferaFormService } from './esfera-form.service';

describe('Esfera Form Service', () => {
  let service: EsferaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EsferaFormService);
  });

  describe('Service methods', () => {
    describe('createEsferaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEsferaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IEsfera should create a new form with FormGroup', () => {
        const formGroup = service.createEsferaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getEsfera', () => {
      it('should return NewEsfera for default Esfera initial value', () => {
        const formGroup = service.createEsferaFormGroup(sampleWithNewData);

        const esfera = service.getEsfera(formGroup) as any;

        expect(esfera).toMatchObject(sampleWithNewData);
      });

      it('should return NewEsfera for empty Esfera initial value', () => {
        const formGroup = service.createEsferaFormGroup();

        const esfera = service.getEsfera(formGroup) as any;

        expect(esfera).toMatchObject({});
      });

      it('should return IEsfera', () => {
        const formGroup = service.createEsferaFormGroup(sampleWithRequiredData);

        const esfera = service.getEsfera(formGroup) as any;

        expect(esfera).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEsfera should not enable id FormControl', () => {
        const formGroup = service.createEsferaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEsfera should disable id FormControl', () => {
        const formGroup = service.createEsferaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
