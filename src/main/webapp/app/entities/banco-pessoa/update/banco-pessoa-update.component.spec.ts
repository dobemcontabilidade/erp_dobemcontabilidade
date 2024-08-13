import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IBanco } from 'app/entities/banco/banco.model';
import { BancoService } from 'app/entities/banco/service/banco.service';
import { IBancoPessoa } from '../banco-pessoa.model';
import { BancoPessoaService } from '../service/banco-pessoa.service';
import { BancoPessoaFormService } from './banco-pessoa-form.service';

import { BancoPessoaUpdateComponent } from './banco-pessoa-update.component';

describe('BancoPessoa Management Update Component', () => {
  let comp: BancoPessoaUpdateComponent;
  let fixture: ComponentFixture<BancoPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bancoPessoaFormService: BancoPessoaFormService;
  let bancoPessoaService: BancoPessoaService;
  let pessoaService: PessoaService;
  let bancoService: BancoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BancoPessoaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BancoPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BancoPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bancoPessoaFormService = TestBed.inject(BancoPessoaFormService);
    bancoPessoaService = TestBed.inject(BancoPessoaService);
    pessoaService = TestBed.inject(PessoaService);
    bancoService = TestBed.inject(BancoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const bancoPessoa: IBancoPessoa = { id: 456 };
      const pessoa: IPessoa = { id: 21685 };
      bancoPessoa.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 4202 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bancoPessoa });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Banco query and add missing value', () => {
      const bancoPessoa: IBancoPessoa = { id: 456 };
      const banco: IBanco = { id: 19047 };
      bancoPessoa.banco = banco;

      const bancoCollection: IBanco[] = [{ id: 14180 }];
      jest.spyOn(bancoService, 'query').mockReturnValue(of(new HttpResponse({ body: bancoCollection })));
      const additionalBancos = [banco];
      const expectedCollection: IBanco[] = [...additionalBancos, ...bancoCollection];
      jest.spyOn(bancoService, 'addBancoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bancoPessoa });
      comp.ngOnInit();

      expect(bancoService.query).toHaveBeenCalled();
      expect(bancoService.addBancoToCollectionIfMissing).toHaveBeenCalledWith(
        bancoCollection,
        ...additionalBancos.map(expect.objectContaining),
      );
      expect(comp.bancosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bancoPessoa: IBancoPessoa = { id: 456 };
      const pessoa: IPessoa = { id: 21471 };
      bancoPessoa.pessoa = pessoa;
      const banco: IBanco = { id: 6636 };
      bancoPessoa.banco = banco;

      activatedRoute.data = of({ bancoPessoa });
      comp.ngOnInit();

      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.bancosSharedCollection).toContain(banco);
      expect(comp.bancoPessoa).toEqual(bancoPessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoPessoa>>();
      const bancoPessoa = { id: 123 };
      jest.spyOn(bancoPessoaFormService, 'getBancoPessoa').mockReturnValue(bancoPessoa);
      jest.spyOn(bancoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bancoPessoa }));
      saveSubject.complete();

      // THEN
      expect(bancoPessoaFormService.getBancoPessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bancoPessoaService.update).toHaveBeenCalledWith(expect.objectContaining(bancoPessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoPessoa>>();
      const bancoPessoa = { id: 123 };
      jest.spyOn(bancoPessoaFormService, 'getBancoPessoa').mockReturnValue({ id: null });
      jest.spyOn(bancoPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoPessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bancoPessoa }));
      saveSubject.complete();

      // THEN
      expect(bancoPessoaFormService.getBancoPessoa).toHaveBeenCalled();
      expect(bancoPessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBancoPessoa>>();
      const bancoPessoa = { id: 123 };
      jest.spyOn(bancoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bancoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bancoPessoaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBanco', () => {
      it('Should forward to bancoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bancoService, 'compareBanco');
        comp.compareBanco(entity, entity2);
        expect(bancoService.compareBanco).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
