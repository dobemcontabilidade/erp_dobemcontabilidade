import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pessoajuridica.test-samples';

import { PessoajuridicaFormService } from './pessoajuridica-form.service';

describe('Pessoajuridica Form Service', () => {
  let service: PessoajuridicaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PessoajuridicaFormService);
  });

  describe('Service methods', () => {
    describe('createPessoajuridicaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPessoajuridicaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            nomeFantasia: expect.any(Object),
            cnpj: expect.any(Object),
          }),
        );
      });

      it('passing IPessoajuridica should create a new form with FormGroup', () => {
        const formGroup = service.createPessoajuridicaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            nomeFantasia: expect.any(Object),
            cnpj: expect.any(Object),
          }),
        );
      });
    });

    describe('getPessoajuridica', () => {
      it('should return NewPessoajuridica for default Pessoajuridica initial value', () => {
        const formGroup = service.createPessoajuridicaFormGroup(sampleWithNewData);

        const pessoajuridica = service.getPessoajuridica(formGroup) as any;

        expect(pessoajuridica).toMatchObject(sampleWithNewData);
      });

      it('should return NewPessoajuridica for empty Pessoajuridica initial value', () => {
        const formGroup = service.createPessoajuridicaFormGroup();

        const pessoajuridica = service.getPessoajuridica(formGroup) as any;

        expect(pessoajuridica).toMatchObject({});
      });

      it('should return IPessoajuridica', () => {
        const formGroup = service.createPessoajuridicaFormGroup(sampleWithRequiredData);

        const pessoajuridica = service.getPessoajuridica(formGroup) as any;

        expect(pessoajuridica).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPessoajuridica should not enable id FormControl', () => {
        const formGroup = service.createPessoajuridicaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPessoajuridica should disable id FormControl', () => {
        const formGroup = service.createPessoajuridicaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
