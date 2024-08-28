import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../socio.test-samples';

import { SocioFormService } from './socio-form.service';

describe('Socio Form Service', () => {
  let service: SocioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocioFormService);
  });

  describe('Service methods', () => {
    describe('createSocioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            prolabore: expect.any(Object),
            percentualSociedade: expect.any(Object),
            adminstrador: expect.any(Object),
            distribuicaoLucro: expect.any(Object),
            responsavelReceita: expect.any(Object),
            percentualDistribuicaoLucro: expect.any(Object),
            funcaoSocio: expect.any(Object),
            pessoaFisica: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing ISocio should create a new form with FormGroup', () => {
        const formGroup = service.createSocioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            prolabore: expect.any(Object),
            percentualSociedade: expect.any(Object),
            adminstrador: expect.any(Object),
            distribuicaoLucro: expect.any(Object),
            responsavelReceita: expect.any(Object),
            percentualDistribuicaoLucro: expect.any(Object),
            funcaoSocio: expect.any(Object),
            pessoaFisica: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getSocio', () => {
      it('should return NewSocio for default Socio initial value', () => {
        const formGroup = service.createSocioFormGroup(sampleWithNewData);

        const socio = service.getSocio(formGroup) as any;

        expect(socio).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocio for empty Socio initial value', () => {
        const formGroup = service.createSocioFormGroup();

        const socio = service.getSocio(formGroup) as any;

        expect(socio).toMatchObject({});
      });

      it('should return ISocio', () => {
        const formGroup = service.createSocioFormGroup(sampleWithRequiredData);

        const socio = service.getSocio(formGroup) as any;

        expect(socio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocio should not enable id FormControl', () => {
        const formGroup = service.createSocioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocio should disable id FormControl', () => {
        const formGroup = service.createSocioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
