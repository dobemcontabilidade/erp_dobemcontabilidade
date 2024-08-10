import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../usuario-erp.test-samples';

import { UsuarioErpFormService } from './usuario-erp-form.service';

describe('UsuarioErp Form Service', () => {
  let service: UsuarioErpFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioErpFormService);
  });

  describe('Service methods', () => {
    describe('createUsuarioErpFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUsuarioErpFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacao: expect.any(Object),
            administrador: expect.any(Object),
          }),
        );
      });

      it('passing IUsuarioErp should create a new form with FormGroup', () => {
        const formGroup = service.createUsuarioErpFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            senha: expect.any(Object),
            dataHoraAtivacao: expect.any(Object),
            dataLimiteAcesso: expect.any(Object),
            situacao: expect.any(Object),
            administrador: expect.any(Object),
          }),
        );
      });
    });

    describe('getUsuarioErp', () => {
      it('should return NewUsuarioErp for default UsuarioErp initial value', () => {
        const formGroup = service.createUsuarioErpFormGroup(sampleWithNewData);

        const usuarioErp = service.getUsuarioErp(formGroup) as any;

        expect(usuarioErp).toMatchObject(sampleWithNewData);
      });

      it('should return NewUsuarioErp for empty UsuarioErp initial value', () => {
        const formGroup = service.createUsuarioErpFormGroup();

        const usuarioErp = service.getUsuarioErp(formGroup) as any;

        expect(usuarioErp).toMatchObject({});
      });

      it('should return IUsuarioErp', () => {
        const formGroup = service.createUsuarioErpFormGroup(sampleWithRequiredData);

        const usuarioErp = service.getUsuarioErp(formGroup) as any;

        expect(usuarioErp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUsuarioErp should not enable id FormControl', () => {
        const formGroup = service.createUsuarioErpFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUsuarioErp should disable id FormControl', () => {
        const formGroup = service.createUsuarioErpFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
