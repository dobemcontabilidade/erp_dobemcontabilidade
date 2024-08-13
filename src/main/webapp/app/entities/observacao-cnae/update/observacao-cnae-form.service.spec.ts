import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../observacao-cnae.test-samples';

import { ObservacaoCnaeFormService } from './observacao-cnae-form.service';

describe('ObservacaoCnae Form Service', () => {
  let service: ObservacaoCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObservacaoCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createObservacaoCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createObservacaoCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            subclasse: expect.any(Object),
          }),
        );
      });

      it('passing IObservacaoCnae should create a new form with FormGroup', () => {
        const formGroup = service.createObservacaoCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            subclasse: expect.any(Object),
          }),
        );
      });
    });

    describe('getObservacaoCnae', () => {
      it('should return NewObservacaoCnae for default ObservacaoCnae initial value', () => {
        const formGroup = service.createObservacaoCnaeFormGroup(sampleWithNewData);

        const observacaoCnae = service.getObservacaoCnae(formGroup) as any;

        expect(observacaoCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewObservacaoCnae for empty ObservacaoCnae initial value', () => {
        const formGroup = service.createObservacaoCnaeFormGroup();

        const observacaoCnae = service.getObservacaoCnae(formGroup) as any;

        expect(observacaoCnae).toMatchObject({});
      });

      it('should return IObservacaoCnae', () => {
        const formGroup = service.createObservacaoCnaeFormGroup(sampleWithRequiredData);

        const observacaoCnae = service.getObservacaoCnae(formGroup) as any;

        expect(observacaoCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IObservacaoCnae should not enable id FormControl', () => {
        const formGroup = service.createObservacaoCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewObservacaoCnae should disable id FormControl', () => {
        const formGroup = service.createObservacaoCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
