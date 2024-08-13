jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';

import { FeedBackContadorParaUsuarioDeleteDialogComponent } from './feed-back-contador-para-usuario-delete-dialog.component';

describe('FeedBackContadorParaUsuario Management Delete Component', () => {
  let comp: FeedBackContadorParaUsuarioDeleteDialogComponent;
  let fixture: ComponentFixture<FeedBackContadorParaUsuarioDeleteDialogComponent>;
  let service: FeedBackContadorParaUsuarioService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FeedBackContadorParaUsuarioDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FeedBackContadorParaUsuarioDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FeedBackContadorParaUsuarioDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FeedBackContadorParaUsuarioService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
