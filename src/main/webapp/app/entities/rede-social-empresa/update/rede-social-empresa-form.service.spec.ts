import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../rede-social-empresa.test-samples';

import { RedeSocialEmpresaFormService } from './rede-social-empresa-form.service';

describe('RedeSocialEmpresa Form Service', () => {
  let service: RedeSocialEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RedeSocialEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createRedeSocialEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            perfil: expect.any(Object),
            urlPerfil: expect.any(Object),
            redeSocial: expect.any(Object),
            pessoajuridica: expect.any(Object),
          }),
        );
      });

      it('passing IRedeSocialEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            perfil: expect.any(Object),
            urlPerfil: expect.any(Object),
            redeSocial: expect.any(Object),
            pessoajuridica: expect.any(Object),
          }),
        );
      });
    });

    describe('getRedeSocialEmpresa', () => {
      it('should return NewRedeSocialEmpresa for default RedeSocialEmpresa initial value', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup(sampleWithNewData);

        const redeSocialEmpresa = service.getRedeSocialEmpresa(formGroup) as any;

        expect(redeSocialEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewRedeSocialEmpresa for empty RedeSocialEmpresa initial value', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup();

        const redeSocialEmpresa = service.getRedeSocialEmpresa(formGroup) as any;

        expect(redeSocialEmpresa).toMatchObject({});
      });

      it('should return IRedeSocialEmpresa', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup(sampleWithRequiredData);

        const redeSocialEmpresa = service.getRedeSocialEmpresa(formGroup) as any;

        expect(redeSocialEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRedeSocialEmpresa should not enable id FormControl', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRedeSocialEmpresa should disable id FormControl', () => {
        const formGroup = service.createRedeSocialEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
