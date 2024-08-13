import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-acesso-usuario-contador.test-samples';

import { GrupoAcessoUsuarioContadorFormService } from './grupo-acesso-usuario-contador-form.service';

describe('GrupoAcessoUsuarioContador Form Service', () => {
  let service: GrupoAcessoUsuarioContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoAcessoUsuarioContadorFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoAcessoUsuarioContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            usuarioContador: expect.any(Object),
            grupoAcessoPadrao: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoAcessoUsuarioContador should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            usuarioContador: expect.any(Object),
            grupoAcessoPadrao: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoAcessoUsuarioContador', () => {
      it('should return NewGrupoAcessoUsuarioContador for default GrupoAcessoUsuarioContador initial value', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup(sampleWithNewData);

        const grupoAcessoUsuarioContador = service.getGrupoAcessoUsuarioContador(formGroup) as any;

        expect(grupoAcessoUsuarioContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoAcessoUsuarioContador for empty GrupoAcessoUsuarioContador initial value', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup();

        const grupoAcessoUsuarioContador = service.getGrupoAcessoUsuarioContador(formGroup) as any;

        expect(grupoAcessoUsuarioContador).toMatchObject({});
      });

      it('should return IGrupoAcessoUsuarioContador', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup(sampleWithRequiredData);

        const grupoAcessoUsuarioContador = service.getGrupoAcessoUsuarioContador(formGroup) as any;

        expect(grupoAcessoUsuarioContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoAcessoUsuarioContador should not enable id FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoAcessoUsuarioContador should disable id FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
