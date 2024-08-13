import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EscolaridadeService } from '../service/escolaridade.service';
import { IEscolaridade } from '../escolaridade.model';
import { EscolaridadeFormService } from './escolaridade-form.service';

import { EscolaridadeUpdateComponent } from './escolaridade-update.component';

describe('Escolaridade Management Update Component', () => {
  let comp: EscolaridadeUpdateComponent;
  let fixture: ComponentFixture<EscolaridadeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let escolaridadeFormService: EscolaridadeFormService;
  let escolaridadeService: EscolaridadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EscolaridadeUpdateComponent],
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
      .overrideTemplate(EscolaridadeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EscolaridadeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    escolaridadeFormService = TestBed.inject(EscolaridadeFormService);
    escolaridadeService = TestBed.inject(EscolaridadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const escolaridade: IEscolaridade = { id: 456 };

      activatedRoute.data = of({ escolaridade });
      comp.ngOnInit();

      expect(comp.escolaridade).toEqual(escolaridade);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridade>>();
      const escolaridade = { id: 123 };
      jest.spyOn(escolaridadeFormService, 'getEscolaridade').mockReturnValue(escolaridade);
      jest.spyOn(escolaridadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escolaridade }));
      saveSubject.complete();

      // THEN
      expect(escolaridadeFormService.getEscolaridade).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(escolaridadeService.update).toHaveBeenCalledWith(expect.objectContaining(escolaridade));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridade>>();
      const escolaridade = { id: 123 };
      jest.spyOn(escolaridadeFormService, 'getEscolaridade').mockReturnValue({ id: null });
      jest.spyOn(escolaridadeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridade: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escolaridade }));
      saveSubject.complete();

      // THEN
      expect(escolaridadeFormService.getEscolaridade).toHaveBeenCalled();
      expect(escolaridadeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscolaridade>>();
      const escolaridade = { id: 123 };
      jest.spyOn(escolaridadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escolaridade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(escolaridadeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
