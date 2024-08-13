import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FeedBackContadorParaUsuarioDetailComponent } from './feed-back-contador-para-usuario-detail.component';

describe('FeedBackContadorParaUsuario Management Detail Component', () => {
  let comp: FeedBackContadorParaUsuarioDetailComponent;
  let fixture: ComponentFixture<FeedBackContadorParaUsuarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeedBackContadorParaUsuarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FeedBackContadorParaUsuarioDetailComponent,
              resolve: { feedBackContadorParaUsuario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FeedBackContadorParaUsuarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedBackContadorParaUsuarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load feedBackContadorParaUsuario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FeedBackContadorParaUsuarioDetailComponent);

      // THEN
      expect(instance.feedBackContadorParaUsuario()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
