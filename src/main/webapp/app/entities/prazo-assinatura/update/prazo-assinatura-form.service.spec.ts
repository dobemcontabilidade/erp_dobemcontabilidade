import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prazo-assinatura.test-samples';

import { PrazoAssinaturaFormService } from './prazo-assinatura-form.service';

describe('PrazoAssinatura Form Service', () => {
  let service: PrazoAssinaturaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrazoAssinaturaFormService);
  });

  describe('Service methods', () => {
    describe('createPrazoAssinaturaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prazo: expect.any(Object),
            meses: expect.any(Object),
          }),
        );
      });

      it('passing IPrazoAssinatura should create a new form with FormGroup', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prazo: expect.any(Object),
            meses: expect.any(Object),
          }),
        );
      });
    });

    describe('getPrazoAssinatura', () => {
      it('should return NewPrazoAssinatura for default PrazoAssinatura initial value', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup(sampleWithNewData);

        const prazoAssinatura = service.getPrazoAssinatura(formGroup) as any;

        expect(prazoAssinatura).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrazoAssinatura for empty PrazoAssinatura initial value', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup();

        const prazoAssinatura = service.getPrazoAssinatura(formGroup) as any;

        expect(prazoAssinatura).toMatchObject({});
      });

      it('should return IPrazoAssinatura', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup(sampleWithRequiredData);

        const prazoAssinatura = service.getPrazoAssinatura(formGroup) as any;

        expect(prazoAssinatura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrazoAssinatura should not enable id FormControl', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrazoAssinatura should disable id FormControl', () => {
        const formGroup = service.createPrazoAssinaturaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
