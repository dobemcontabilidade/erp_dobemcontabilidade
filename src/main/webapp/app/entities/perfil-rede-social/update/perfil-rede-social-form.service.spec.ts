import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-rede-social.test-samples';

import { PerfilRedeSocialFormService } from './perfil-rede-social-form.service';

describe('PerfilRedeSocial Form Service', () => {
  let service: PerfilRedeSocialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilRedeSocialFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilRedeSocialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rede: expect.any(Object),
            urlPerfil: expect.any(Object),
            tipoRede: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilRedeSocial should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rede: expect.any(Object),
            urlPerfil: expect.any(Object),
            tipoRede: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilRedeSocial', () => {
      it('should return NewPerfilRedeSocial for default PerfilRedeSocial initial value', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup(sampleWithNewData);

        const perfilRedeSocial = service.getPerfilRedeSocial(formGroup) as any;

        expect(perfilRedeSocial).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilRedeSocial for empty PerfilRedeSocial initial value', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup();

        const perfilRedeSocial = service.getPerfilRedeSocial(formGroup) as any;

        expect(perfilRedeSocial).toMatchObject({});
      });

      it('should return IPerfilRedeSocial', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup(sampleWithRequiredData);

        const perfilRedeSocial = service.getPerfilRedeSocial(formGroup) as any;

        expect(perfilRedeSocial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilRedeSocial should not enable id FormControl', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilRedeSocial should disable id FormControl', () => {
        const formGroup = service.createPerfilRedeSocialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
