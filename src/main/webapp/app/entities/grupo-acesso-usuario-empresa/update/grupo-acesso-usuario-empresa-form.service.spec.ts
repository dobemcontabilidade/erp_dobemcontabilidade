import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-acesso-usuario-empresa.test-samples';

import { GrupoAcessoUsuarioEmpresaFormService } from './grupo-acesso-usuario-empresa-form.service';

describe('GrupoAcessoUsuarioEmpresa Form Service', () => {
  let service: GrupoAcessoUsuarioEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoAcessoUsuarioEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoAcessoUsuarioEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoAcessoUsuarioEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoAcessoUsuarioEmpresa', () => {
      it('should return NewGrupoAcessoUsuarioEmpresa for default GrupoAcessoUsuarioEmpresa initial value', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup(sampleWithNewData);

        const grupoAcessoUsuarioEmpresa = service.getGrupoAcessoUsuarioEmpresa(formGroup) as any;

        expect(grupoAcessoUsuarioEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoAcessoUsuarioEmpresa for empty GrupoAcessoUsuarioEmpresa initial value', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup();

        const grupoAcessoUsuarioEmpresa = service.getGrupoAcessoUsuarioEmpresa(formGroup) as any;

        expect(grupoAcessoUsuarioEmpresa).toMatchObject({});
      });

      it('should return IGrupoAcessoUsuarioEmpresa', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup(sampleWithRequiredData);

        const grupoAcessoUsuarioEmpresa = service.getGrupoAcessoUsuarioEmpresa(formGroup) as any;

        expect(grupoAcessoUsuarioEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoAcessoUsuarioEmpresa should not enable id FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoAcessoUsuarioEmpresa should disable id FormControl', () => {
        const formGroup = service.createGrupoAcessoUsuarioEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
