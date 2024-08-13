import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../modulo.test-samples';

import { ModuloFormService } from './modulo-form.service';

describe('Modulo Form Service', () => {
  let service: ModuloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModuloFormService);
  });

  describe('Service methods', () => {
    describe('createModuloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createModuloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            sistema: expect.any(Object),
          }),
        );
      });

      it('passing IModulo should create a new form with FormGroup', () => {
        const formGroup = service.createModuloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            sistema: expect.any(Object),
          }),
        );
      });
    });

    describe('getModulo', () => {
      it('should return NewModulo for default Modulo initial value', () => {
        const formGroup = service.createModuloFormGroup(sampleWithNewData);

        const modulo = service.getModulo(formGroup) as any;

        expect(modulo).toMatchObject(sampleWithNewData);
      });

      it('should return NewModulo for empty Modulo initial value', () => {
        const formGroup = service.createModuloFormGroup();

        const modulo = service.getModulo(formGroup) as any;

        expect(modulo).toMatchObject({});
      });

      it('should return IModulo', () => {
        const formGroup = service.createModuloFormGroup(sampleWithRequiredData);

        const modulo = service.getModulo(formGroup) as any;

        expect(modulo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IModulo should not enable id FormControl', () => {
        const formGroup = service.createModuloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewModulo should disable id FormControl', () => {
        const formGroup = service.createModuloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
