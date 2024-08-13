import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-acesso-empresa.test-samples';

import { GrupoAcessoEmpresaFormService } from './grupo-acesso-empresa-form.service';

describe('GrupoAcessoEmpresa Form Service', () => {
  let service: GrupoAcessoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoAcessoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoAcessoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoAcessoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoAcessoEmpresa', () => {
      it('should return NewGrupoAcessoEmpresa for default GrupoAcessoEmpresa initial value', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup(sampleWithNewData);

        const grupoAcessoEmpresa = service.getGrupoAcessoEmpresa(formGroup) as any;

        expect(grupoAcessoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoAcessoEmpresa for empty GrupoAcessoEmpresa initial value', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup();

        const grupoAcessoEmpresa = service.getGrupoAcessoEmpresa(formGroup) as any;

        expect(grupoAcessoEmpresa).toMatchObject({});
      });

      it('should return IGrupoAcessoEmpresa', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);

        const grupoAcessoEmpresa = service.getGrupoAcessoEmpresa(formGroup) as any;

        expect(grupoAcessoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoAcessoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoAcessoEmpresa should disable id FormControl', () => {
        const formGroup = service.createGrupoAcessoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
