import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../funcionalidade-grupo-acesso-empresa.test-samples';

import { FuncionalidadeGrupoAcessoEmpresaFormService } from './funcionalidade-grupo-acesso-empresa-form.service';

describe('FuncionalidadeGrupoAcessoEmpresa Form Service', () => {
  let service: FuncionalidadeGrupoAcessoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuncionalidadeGrupoAcessoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createFuncionalidadeGrupoAcessoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativa: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            funcionalidade: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
            permisao: expect.any(Object),
          }),
        );
      });

      it('passing IFuncionalidadeGrupoAcessoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativa: expect.any(Object),
            dataExpiracao: expect.any(Object),
            ilimitado: expect.any(Object),
            desabilitar: expect.any(Object),
            funcionalidade: expect.any(Object),
            grupoAcessoEmpresa: expect.any(Object),
            permisao: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuncionalidadeGrupoAcessoEmpresa', () => {
      it('should return NewFuncionalidadeGrupoAcessoEmpresa for default FuncionalidadeGrupoAcessoEmpresa initial value', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup(sampleWithNewData);

        const funcionalidadeGrupoAcessoEmpresa = service.getFuncionalidadeGrupoAcessoEmpresa(formGroup) as any;

        expect(funcionalidadeGrupoAcessoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuncionalidadeGrupoAcessoEmpresa for empty FuncionalidadeGrupoAcessoEmpresa initial value', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup();

        const funcionalidadeGrupoAcessoEmpresa = service.getFuncionalidadeGrupoAcessoEmpresa(formGroup) as any;

        expect(funcionalidadeGrupoAcessoEmpresa).toMatchObject({});
      });

      it('should return IFuncionalidadeGrupoAcessoEmpresa', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);

        const funcionalidadeGrupoAcessoEmpresa = service.getFuncionalidadeGrupoAcessoEmpresa(formGroup) as any;

        expect(funcionalidadeGrupoAcessoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuncionalidadeGrupoAcessoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuncionalidadeGrupoAcessoEmpresa should disable id FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
