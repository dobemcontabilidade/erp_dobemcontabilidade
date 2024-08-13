import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../funcionalidade-grupo-acesso-padrao.test-samples';

import { FuncionalidadeGrupoAcessoPadraoFormService } from './funcionalidade-grupo-acesso-padrao-form.service';

describe('FuncionalidadeGrupoAcessoPadrao Form Service', () => {
  let service: FuncionalidadeGrupoAcessoPadraoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuncionalidadeGrupoAcessoPadraoFormService);
  });

  describe('Service methods', () => {
    describe('createFuncionalidadeGrupoAcessoPadraoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            autorizado: expect.any(Object),
            dataExpiracao: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            funcionalidade: expect.any(Object),
            grupoAcessoPadrao: expect.any(Object),
            permisao: expect.any(Object),
          }),
        );
      });

      it('passing IFuncionalidadeGrupoAcessoPadrao should create a new form with FormGroup', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            autorizado: expect.any(Object),
            dataExpiracao: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            funcionalidade: expect.any(Object),
            grupoAcessoPadrao: expect.any(Object),
            permisao: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuncionalidadeGrupoAcessoPadrao', () => {
      it('should return NewFuncionalidadeGrupoAcessoPadrao for default FuncionalidadeGrupoAcessoPadrao initial value', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup(sampleWithNewData);

        const funcionalidadeGrupoAcessoPadrao = service.getFuncionalidadeGrupoAcessoPadrao(formGroup) as any;

        expect(funcionalidadeGrupoAcessoPadrao).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuncionalidadeGrupoAcessoPadrao for empty FuncionalidadeGrupoAcessoPadrao initial value', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup();

        const funcionalidadeGrupoAcessoPadrao = service.getFuncionalidadeGrupoAcessoPadrao(formGroup) as any;

        expect(funcionalidadeGrupoAcessoPadrao).toMatchObject({});
      });

      it('should return IFuncionalidadeGrupoAcessoPadrao', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup(sampleWithRequiredData);

        const funcionalidadeGrupoAcessoPadrao = service.getFuncionalidadeGrupoAcessoPadrao(formGroup) as any;

        expect(funcionalidadeGrupoAcessoPadrao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuncionalidadeGrupoAcessoPadrao should not enable id FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuncionalidadeGrupoAcessoPadrao should disable id FormControl', () => {
        const formGroup = service.createFuncionalidadeGrupoAcessoPadraoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
