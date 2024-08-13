import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-acesso-empresa-usuario-contador.test-samples';

import { GrupoAcessoEmpresaUsuarioContadorFormService } from './grupo-acesso-empresa-usuario-contador-form.service';

describe('GrupoAcessoEmpresaUsuarioContador Form Service', () => {
  let service: GrupoAcessoEmpresaUsuarioContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoAcessoEmpresaUsuarioContadorFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoAcessoEmpresaUsuarioContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            usuarioContador: expect.any(Object),
            permisao: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoAcessoEmpresaUsuarioContador should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            usuarioContador: expect.any(Object),
            permisao: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoAcessoEmpresaUsuarioContador', () => {
      it('should return NewGrupoAcessoEmpresaUsuarioContador for default GrupoAcessoEmpresaUsuarioContador initial value', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup(sampleWithNewData);

        const grupoAcessoEmpresaUsuarioContador = service.getGrupoAcessoEmpresaUsuarioContador(formGroup) as any;

        expect(grupoAcessoEmpresaUsuarioContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoAcessoEmpresaUsuarioContador for empty GrupoAcessoEmpresaUsuarioContador initial value', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup();

        const grupoAcessoEmpresaUsuarioContador = service.getGrupoAcessoEmpresaUsuarioContador(formGroup) as any;

        expect(grupoAcessoEmpresaUsuarioContador).toMatchObject({});
      });

      it('should return IGrupoAcessoEmpresaUsuarioContador', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup(sampleWithRequiredData);

        const grupoAcessoEmpresaUsuarioContador = service.getGrupoAcessoEmpresaUsuarioContador(formGroup) as any;

        expect(grupoAcessoEmpresaUsuarioContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoAcessoEmpresaUsuarioContador should not enable id FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoAcessoEmpresaUsuarioContador should disable id FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaUsuarioContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
