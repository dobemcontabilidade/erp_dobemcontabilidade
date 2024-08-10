import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tipo-denuncia.test-samples';

import { TipoDenunciaFormService } from './tipo-denuncia-form.service';

describe('TipoDenuncia Form Service', () => {
  let service: TipoDenunciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoDenunciaFormService);
  });

  describe('Service methods', () => {
    describe('createTipoDenunciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTipoDenunciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing ITipoDenuncia should create a new form with FormGroup', () => {
        const formGroup = service.createTipoDenunciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getTipoDenuncia', () => {
      it('should return NewTipoDenuncia for default TipoDenuncia initial value', () => {
        const formGroup = service.createTipoDenunciaFormGroup(sampleWithNewData);

        const tipoDenuncia = service.getTipoDenuncia(formGroup) as any;

        expect(tipoDenuncia).toMatchObject(sampleWithNewData);
      });

      it('should return NewTipoDenuncia for empty TipoDenuncia initial value', () => {
        const formGroup = service.createTipoDenunciaFormGroup();

        const tipoDenuncia = service.getTipoDenuncia(formGroup) as any;

        expect(tipoDenuncia).toMatchObject({});
      });

      it('should return ITipoDenuncia', () => {
        const formGroup = service.createTipoDenunciaFormGroup(sampleWithRequiredData);

        const tipoDenuncia = service.getTipoDenuncia(formGroup) as any;

        expect(tipoDenuncia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITipoDenuncia should not enable id FormControl', () => {
        const formGroup = service.createTipoDenunciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTipoDenuncia should disable id FormControl', () => {
        const formGroup = service.createTipoDenunciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
