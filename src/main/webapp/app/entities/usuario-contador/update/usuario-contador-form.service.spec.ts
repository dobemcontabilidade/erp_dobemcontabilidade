import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../usuario-contador.test-samples';

import { UsuarioContadorFormService } from './usuario-contador-form.service';

describe('UsuarioContador Form Service', () => {
  let service: UsuarioContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioContadorFormService);
  });

  describe('Service methods', () => {
    describe('createUsuarioContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUsuarioContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });

      it('passing IUsuarioContador should create a new form with FormGroup', () => {
        const formGroup = service.createUsuarioContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            token: expect.any(Object),
            ativo: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getUsuarioContador', () => {
      it('should return NewUsuarioContador for default UsuarioContador initial value', () => {
        const formGroup = service.createUsuarioContadorFormGroup(sampleWithNewData);

        const usuarioContador = service.getUsuarioContador(formGroup) as any;

        expect(usuarioContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewUsuarioContador for empty UsuarioContador initial value', () => {
        const formGroup = service.createUsuarioContadorFormGroup();

        const usuarioContador = service.getUsuarioContador(formGroup) as any;

        expect(usuarioContador).toMatchObject({});
      });

      it('should return IUsuarioContador', () => {
        const formGroup = service.createUsuarioContadorFormGroup(sampleWithRequiredData);

        const usuarioContador = service.getUsuarioContador(formGroup) as any;

        expect(usuarioContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUsuarioContador should not enable id FormControl', () => {
        const formGroup = service.createUsuarioContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUsuarioContador should disable id FormControl', () => {
        const formGroup = service.createUsuarioContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
