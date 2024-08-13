import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-acesso-usuario.test-samples';

import { PerfilAcessoUsuarioFormService } from './perfil-acesso-usuario-form.service';

describe('PerfilAcessoUsuario Form Service', () => {
  let service: PerfilAcessoUsuarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilAcessoUsuarioFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilAcessoUsuarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            autorizado: expect.any(Object),
            dataExpiracao: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilAcessoUsuario should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            autorizado: expect.any(Object),
            dataExpiracao: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilAcessoUsuario', () => {
      it('should return NewPerfilAcessoUsuario for default PerfilAcessoUsuario initial value', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup(sampleWithNewData);

        const perfilAcessoUsuario = service.getPerfilAcessoUsuario(formGroup) as any;

        expect(perfilAcessoUsuario).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilAcessoUsuario for empty PerfilAcessoUsuario initial value', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup();

        const perfilAcessoUsuario = service.getPerfilAcessoUsuario(formGroup) as any;

        expect(perfilAcessoUsuario).toMatchObject({});
      });

      it('should return IPerfilAcessoUsuario', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup(sampleWithRequiredData);

        const perfilAcessoUsuario = service.getPerfilAcessoUsuario(formGroup) as any;

        expect(perfilAcessoUsuario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilAcessoUsuario should not enable id FormControl', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilAcessoUsuario should disable id FormControl', () => {
        const formGroup = service.createPerfilAcessoUsuarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
