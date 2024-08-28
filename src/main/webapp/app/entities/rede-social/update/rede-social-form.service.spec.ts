import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../rede-social.test-samples';

import { RedeSocialFormService } from './rede-social-form.service';

describe('RedeSocial Form Service', () => {
  let service: RedeSocialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RedeSocialFormService);
  });

  describe('Service methods', () => {
    describe('createRedeSocialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRedeSocialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            url: expect.any(Object),
            logo: expect.any(Object),
          }),
        );
      });

      it('passing IRedeSocial should create a new form with FormGroup', () => {
        const formGroup = service.createRedeSocialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            url: expect.any(Object),
            logo: expect.any(Object),
          }),
        );
      });
    });

    describe('getRedeSocial', () => {
      it('should return NewRedeSocial for default RedeSocial initial value', () => {
        const formGroup = service.createRedeSocialFormGroup(sampleWithNewData);

        const redeSocial = service.getRedeSocial(formGroup) as any;

        expect(redeSocial).toMatchObject(sampleWithNewData);
      });

      it('should return NewRedeSocial for empty RedeSocial initial value', () => {
        const formGroup = service.createRedeSocialFormGroup();

        const redeSocial = service.getRedeSocial(formGroup) as any;

        expect(redeSocial).toMatchObject({});
      });

      it('should return IRedeSocial', () => {
        const formGroup = service.createRedeSocialFormGroup(sampleWithRequiredData);

        const redeSocial = service.getRedeSocial(formGroup) as any;

        expect(redeSocial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRedeSocial should not enable id FormControl', () => {
        const formGroup = service.createRedeSocialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRedeSocial should disable id FormControl', () => {
        const formGroup = service.createRedeSocialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
