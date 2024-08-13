import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../usuario-gestao.test-samples';

import { UsuarioGestaoFormService } from './usuario-gestao-form.service';

describe('UsuarioGestao Form Service', () => {
  let service: UsuarioGestaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioGestaoFormService);
  });

  describe('Service methods', () => {
    describe('createUsuarioGestaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUsuarioGestaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacaoUsuarioGestao: expect.any(Object),
            administrador: expect.any(Object),
          }),
        );
      });

      it('passing IUsuarioGestao should create a new form with FormGroup', () => {
        const formGroup = service.createUsuarioGestaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacaoUsuarioGestao: expect.any(Object),
            administrador: expect.any(Object),
          }),
        );
      });
    });

    describe('getUsuarioGestao', () => {
      it('should return NewUsuarioGestao for default UsuarioGestao initial value', () => {
        const formGroup = service.createUsuarioGestaoFormGroup(sampleWithNewData);

        const usuarioGestao = service.getUsuarioGestao(formGroup) as any;

        expect(usuarioGestao).toMatchObject(sampleWithNewData);
      });

      it('should return NewUsuarioGestao for empty UsuarioGestao initial value', () => {
        const formGroup = service.createUsuarioGestaoFormGroup();

        const usuarioGestao = service.getUsuarioGestao(formGroup) as any;

        expect(usuarioGestao).toMatchObject({});
      });

      it('should return IUsuarioGestao', () => {
        const formGroup = service.createUsuarioGestaoFormGroup(sampleWithRequiredData);

        const usuarioGestao = service.getUsuarioGestao(formGroup) as any;

        expect(usuarioGestao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUsuarioGestao should not enable id FormControl', () => {
        const formGroup = service.createUsuarioGestaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUsuarioGestao should disable id FormControl', () => {
        const formGroup = service.createUsuarioGestaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
