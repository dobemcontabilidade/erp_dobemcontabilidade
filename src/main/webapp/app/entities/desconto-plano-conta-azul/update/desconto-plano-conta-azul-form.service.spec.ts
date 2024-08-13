import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../desconto-plano-conta-azul.test-samples';

import { DescontoPlanoContaAzulFormService } from './desconto-plano-conta-azul-form.service';

describe('DescontoPlanoContaAzul Form Service', () => {
  let service: DescontoPlanoContaAzulFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DescontoPlanoContaAzulFormService);
  });

  describe('Service methods', () => {
    describe('createDescontoPlanoContaAzulFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            planoContaAzul: expect.any(Object),
            periodoPagamento: expect.any(Object),
          }),
        );
      });

      it('passing IDescontoPlanoContaAzul should create a new form with FormGroup', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            planoContaAzul: expect.any(Object),
            periodoPagamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getDescontoPlanoContaAzul', () => {
      it('should return NewDescontoPlanoContaAzul for default DescontoPlanoContaAzul initial value', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup(sampleWithNewData);

        const descontoPlanoContaAzul = service.getDescontoPlanoContaAzul(formGroup) as any;

        expect(descontoPlanoContaAzul).toMatchObject(sampleWithNewData);
      });

      it('should return NewDescontoPlanoContaAzul for empty DescontoPlanoContaAzul initial value', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup();

        const descontoPlanoContaAzul = service.getDescontoPlanoContaAzul(formGroup) as any;

        expect(descontoPlanoContaAzul).toMatchObject({});
      });

      it('should return IDescontoPlanoContaAzul', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup(sampleWithRequiredData);

        const descontoPlanoContaAzul = service.getDescontoPlanoContaAzul(formGroup) as any;

        expect(descontoPlanoContaAzul).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDescontoPlanoContaAzul should not enable id FormControl', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDescontoPlanoContaAzul should disable id FormControl', () => {
        const formGroup = service.createDescontoPlanoContaAzulFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
