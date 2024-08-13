import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEscolaridade } from 'app/entities/escolaridade/escolaridade.model';
import { EscolaridadeService } from 'app/entities/escolaridade/service/escolaridade.service';
import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';
import { EscolaridadePessoaService } from '../service/escolaridade-pessoa.service';
import { EscolaridadePessoaFormService } from './escolaridade-pessoa-form.service';

import { EscolaridadePessoaUpdateComponent } from './escolaridade-pessoa-update.component';

describe('EscolaridadePessoa Management Update Component', () => {
  let comp: EscolaridadePessoaUpdateComponent;
  let fixture: ComponentFixture<EscolaridadePessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let escolaridadePessoaFormService: EscolaridadePessoaFormService;
  let escolaridadePessoaService: EscolaridadePessoaService;
  let pessoaService: PessoaService;
  let escolaridadeService: EscolaridadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EscolaridadePessoaUpdateComponent],
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
      .overrideTemplate(EscolaridadePessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EscolaridadePessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    escolaridadePessoaFormService = TestBed.inject(EscolaridadePessoaFormService);
    escolaridadePessoaService = TestBed.inject(EscolaridadePessoaService);
    pessoaService = TestBed.inject(PessoaService);
    escolaridadeService = TestBed.inject(EscolaridadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const escolaridadePessoa: IEscolaridadePessoa = { id: 456 };
      const pessoa: IPessoa = { id: 27980 };
      escolaridadePessoa.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 31111 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ escolaridadePessoa });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Escolaridade query and add missing value', () => {
      const escolaridadePessoa: IEscolaridadePessoa = { id: 456 };
      const escolaridade: IEscolaridade = { id: 20797 };
      escolaridadePessoa.escolaridade = escolaridade;

      const escolaridadeCollection: IEscolaridade[] = [{ id: 29450 }];
      jest.spyOn(escolaridadeService, 'query').mockReturnValue(of(new HttpResponse({ body: escolaridadeCollection })));
      const additionalEscolaridades = [escolaridade];
      const expectedCollection: IEscolaridade[] = [...additionalEscolaridades, ...escolaridadeCollection];
      jest.spyOn(escolaridadeService, 'addEscolaridadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ escolaridadePessoa });
      comp.ngOnInit();

      expect(escolaridadeService.query).toHaveBeenCalled();
      expect(escolaridadeService.addEscolaridadeToCollectionIfMissing).toHaveBeenCalledWith(
        escolaridadeCollection,
        ...additionalEscolaridades.map(expect.objectContaining),
      );
      expect(comp.escolaridadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const escolaridadePessoa: IEscolaridadePessoa = { id: 456 };
      const pessoa: IPessoa = { id: 18799 };
      escolaridadePessoa.pessoa = pessoa;
      const escolaridade: IEscolaridade = { id: 2040 };
      escolaridadePessoa.escolaridade = escolaridade;

      activatedRoute.data = of({ escolaridadePessoa });
      comp.ngOnInit();

      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.escolaridadesSharedCollection).toContain(escolaridade);
      expect(comp.escolaridadePessoa).toEqual(escolaridadePessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridadePessoa>>();
      const escolaridadePessoa = { id: 123 };
      jest.spyOn(escolaridadePessoaFormService, 'getEscolaridadePessoa').mockReturnValue(escolaridadePessoa);
      jest.spyOn(escolaridadePessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridadePessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escolaridadePessoa }));
      saveSubject.complete();

      // THEN
      expect(escolaridadePessoaFormService.getEscolaridadePessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(escolaridadePessoaService.update).toHaveBeenCalledWith(expect.objectContaining(escolaridadePessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridadePessoa>>();
      const escolaridadePessoa = { id: 123 };
      jest.spyOn(escolaridadePessoaFormService, 'getEscolaridadePessoa').mockReturnValue({ id: null });
      jest.spyOn(escolaridadePessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridadePessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escolaridadePessoa }));
      saveSubject.complete();

      // THEN
      expect(escolaridadePessoaFormService.getEscolaridadePessoa).toHaveBeenCalled();
      expect(escolaridadePessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridadePessoa>>();
      const escolaridadePessoa = { id: 123 };
      jest.spyOn(escolaridadePessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridadePessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(escolaridadePessoaService.update).toHaveBeenCalled();
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

    describe('compareEscolaridade', () => {
      it('Should forward to escolaridadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(escolaridadeService, 'compareEscolaridade');
        comp.compareEscolaridade(entity, entity2);
        expect(escolaridadeService.compareEscolaridade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
