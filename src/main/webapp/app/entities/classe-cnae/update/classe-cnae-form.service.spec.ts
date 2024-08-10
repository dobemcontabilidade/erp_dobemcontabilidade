import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../classe-cnae.test-samples';

import { ClasseCnaeFormService } from './classe-cnae-form.service';

describe('ClasseCnae Form Service', () => {
  let service: ClasseCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClasseCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createClasseCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClasseCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            grupo: expect.any(Object),
          }),
        );
      });

      it('passing IClasseCnae should create a new form with FormGroup', () => {
        const formGroup = service.createClasseCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            grupo: expect.any(Object),
          }),
        );
      });
    });

    describe('getClasseCnae', () => {
      it('should return NewClasseCnae for default ClasseCnae initial value', () => {
        const formGroup = service.createClasseCnaeFormGroup(sampleWithNewData);

        const classeCnae = service.getClasseCnae(formGroup) as any;

        expect(classeCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewClasseCnae for empty ClasseCnae initial value', () => {
        const formGroup = service.createClasseCnaeFormGroup();

        const classeCnae = service.getClasseCnae(formGroup) as any;

        expect(classeCnae).toMatchObject({});
      });

      it('should return IClasseCnae', () => {
        const formGroup = service.createClasseCnaeFormGroup(sampleWithRequiredData);

        const classeCnae = service.getClasseCnae(formGroup) as any;

        expect(classeCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClasseCnae should not enable id FormControl', () => {
        const formGroup = service.createClasseCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClasseCnae should disable id FormControl', () => {
        const formGroup = service.createClasseCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
