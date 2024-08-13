import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../permisao.test-samples';

import { PermisaoFormService } from './permisao-form.service';

describe('Permisao Form Service', () => {
  let service: PermisaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PermisaoFormService);
  });

  describe('Service methods', () => {
    describe('createPermisaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPermisaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            label: expect.any(Object),
          }),
        );
      });

      it('passing IPermisao should create a new form with FormGroup', () => {
        const formGroup = service.createPermisaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            label: expect.any(Object),
          }),
        );
      });
    });

    describe('getPermisao', () => {
      it('should return NewPermisao for default Permisao initial value', () => {
        const formGroup = service.createPermisaoFormGroup(sampleWithNewData);

        const permisao = service.getPermisao(formGroup) as any;

        expect(permisao).toMatchObject(sampleWithNewData);
      });

      it('should return NewPermisao for empty Permisao initial value', () => {
        const formGroup = service.createPermisaoFormGroup();

        const permisao = service.getPermisao(formGroup) as any;

        expect(permisao).toMatchObject({});
      });

      it('should return IPermisao', () => {
        const formGroup = service.createPermisaoFormGroup(sampleWithRequiredData);

        const permisao = service.getPermisao(formGroup) as any;

        expect(permisao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPermisao should not enable id FormControl', () => {
        const formGroup = service.createPermisaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPermisao should disable id FormControl', () => {
        const formGroup = service.createPermisaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
