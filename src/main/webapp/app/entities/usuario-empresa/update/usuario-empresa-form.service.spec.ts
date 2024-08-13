import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../usuario-empresa.test-samples';

import { UsuarioEmpresaFormService } from './usuario-empresa-form.service';

describe('UsuarioEmpresa Form Service', () => {
  let service: UsuarioEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createUsuarioEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacaoUsuarioEmpresa: expect.any(Object),
            tipoUsuarioEmpresaEnum: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IUsuarioEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacaoUsuarioEmpresa: expect.any(Object),
            tipoUsuarioEmpresaEnum: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getUsuarioEmpresa', () => {
      it('should return NewUsuarioEmpresa for default UsuarioEmpresa initial value', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup(sampleWithNewData);

        const usuarioEmpresa = service.getUsuarioEmpresa(formGroup) as any;

        expect(usuarioEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewUsuarioEmpresa for empty UsuarioEmpresa initial value', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup();

        const usuarioEmpresa = service.getUsuarioEmpresa(formGroup) as any;

        expect(usuarioEmpresa).toMatchObject({});
      });

      it('should return IUsuarioEmpresa', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup(sampleWithRequiredData);

        const usuarioEmpresa = service.getUsuarioEmpresa(formGroup) as any;

        expect(usuarioEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUsuarioEmpresa should not enable id FormControl', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUsuarioEmpresa should disable id FormControl', () => {
        const formGroup = service.createUsuarioEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
