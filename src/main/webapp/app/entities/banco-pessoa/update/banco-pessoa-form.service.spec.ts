import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../banco-pessoa.test-samples';

import { BancoPessoaFormService } from './banco-pessoa-form.service';

describe('BancoPessoa Form Service', () => {
  let service: BancoPessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BancoPessoaFormService);
  });

  describe('Service methods', () => {
    describe('createBancoPessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBancoPessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agencia: expect.any(Object),
            conta: expect.any(Object),
            tipoConta: expect.any(Object),
            principal: expect.any(Object),
            pessoa: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });

      it('passing IBancoPessoa should create a new form with FormGroup', () => {
        const formGroup = service.createBancoPessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agencia: expect.any(Object),
            conta: expect.any(Object),
            tipoConta: expect.any(Object),
            principal: expect.any(Object),
            pessoa: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });
    });

    describe('getBancoPessoa', () => {
      it('should return NewBancoPessoa for default BancoPessoa initial value', () => {
        const formGroup = service.createBancoPessoaFormGroup(sampleWithNewData);

        const bancoPessoa = service.getBancoPessoa(formGroup) as any;

        expect(bancoPessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewBancoPessoa for empty BancoPessoa initial value', () => {
        const formGroup = service.createBancoPessoaFormGroup();

        const bancoPessoa = service.getBancoPessoa(formGroup) as any;

        expect(bancoPessoa).toMatchObject({});
      });

      it('should return IBancoPessoa', () => {
        const formGroup = service.createBancoPessoaFormGroup(sampleWithRequiredData);

        const bancoPessoa = service.getBancoPessoa(formGroup) as any;

        expect(bancoPessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBancoPessoa should not enable id FormControl', () => {
        const formGroup = service.createBancoPessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBancoPessoa should disable id FormControl', () => {
        const formGroup = service.createBancoPessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
